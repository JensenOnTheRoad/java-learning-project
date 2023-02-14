package spring.redis;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import spring.config.GsonLocalDate;
import spring.config.GsonLocalDateTime;

/**
 * @author jensen_deng
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUtils {
  private final StringRedisTemplate redisTemplate;
  private final Gson gson =
      new GsonBuilder()
          .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime())
          .registerTypeAdapter(LocalDate.class, new GsonLocalDate())
          .registerTypeAdapter(Long.class, new base_on_spring.redis.GsonLong())
          .create();

  public <T> T get(String key, Class rawClass, Class... classes) {
    String content = redisTemplate.opsForValue().get(key);
    Type type = TypeToken.getParameterized(rawClass, classes).getType();
    return gson.fromJson(content, type);
  }

  public void set(String key, Object value) {
    redisTemplate.opsForValue().set(key, gson.toJson(value));
  }

  public Boolean hasKey(String key) {
    return redisTemplate.opsForValue().getOperations().hasKey(key);
  }

  public void expire(String key, long timeout, TimeUnit unit) {
    redisTemplate.expire(key, timeout, unit);
  }

  public void delete(String key) {
    redisTemplate.delete(key);
  }

  // region scan 使用 scan 命令代替 keys 命令，在大数据量的情况下可以提高查询效率

  /**
   * 分页获取指定格式key
   *
   * @param patternKey 匹配key
   * @param pageIndex 页码
   * @param pageSize 页大小
   * @return {@link base_on_spring.redis.PageResult}<{@link String}>
   */
  @Generated
  public base_on_spring.redis.PageResult<String> findKeysForPage(
      String patternKey, int pageIndex, int pageSize) {
    base_on_spring.redis.PageResult<String> result = base_on_spring.redis.PageResult.empty();
    List<String> keys = new ArrayList<>();

    RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
    RedisConnection connection = Objects.requireNonNull(factory).getConnection();
    ScanOptions options =
        ScanOptions.scanOptions()
            .match("*%s*".formatted(patternKey))
            .count(Integer.MAX_VALUE)
            .build();

    try (Cursor<byte[]> cursor = connection.scan(options)) {
      int tmpIndex = 0;
      int startIndex = (pageIndex - 1) * pageSize;
      int end = pageIndex * pageSize;
      while (cursor.hasNext()) {
        String key = new String(cursor.next());
        if (tmpIndex >= startIndex && tmpIndex < end) {
          keys.add(key);
        }
        tmpIndex++;
      }
      result = base_on_spring.redis.PageResult.of(pageIndex, pageSize, keys.size(), keys);
      RedisConnectionUtils.releaseConnection(connection, factory);
    } catch (Exception e) {
      log.warn("Redis连接关闭异常，", e);
    }
    return result;
  }

  /**
   * 获取指定格式keys
   *
   * @param patternKey 匹配key
   * @return {@link List}<{@link String}>
   */
  @Generated
  public List<String> findKeys(String patternKey) {
    List<String> result = new ArrayList<>();

    RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
    RedisConnection rc = Objects.requireNonNull(factory).getConnection();
    ScanOptions options =
        ScanOptions.scanOptions()
            .match("*%s*".formatted(patternKey))
            .count(Integer.MAX_VALUE)
            .build();

    try (Cursor<byte[]> cursor = rc.scan(options)) {
      while (cursor.hasNext()) {
        String key = new String(cursor.next());
        result.add(key);
      }
      RedisConnectionUtils.releaseConnection(rc, factory);
    } catch (Exception e) {
      log.warn("Redis连接关闭异常，", e);
    }
    return result;
  }

  // endregion
}
