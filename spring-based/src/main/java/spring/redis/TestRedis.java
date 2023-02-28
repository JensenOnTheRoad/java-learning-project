package spring.redis;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import spring.SpringBootTestBase;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestRedis extends SpringBootTestBase {
  @Autowired private StringRedisTemplate redisTemplate;

  private static final int REDIS_KEEP_TIME = 1; // 过期时间
  private final Type type = new TypeToken<User>() {}.getType();
  private Gson gson;

  @BeforeEach
  void setUp() {
    gson = getGson();
  }
  // region RedisTemplate

  @Test
  @DisplayName("添加一个对象缓存")
  public void testRedisTemplateObj() {
    String key = "ZhaoYun";
    if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
      log.info("缓存{}没有存在", key);

      User user = User.builder().id(1001).name(key).build();
      setCache(key, gson.toJson(user));
      log.info("添加缓存");

      String cache = getCache(key).toString();
      User user1 = gson.fromJson(cache, type);
      Assertions.assertThat(user).isEqualTo(user1);
      Assertions.assertThat(deleteCache(key)).isTrue();
    }
  }

  @Test
  @DisplayName("设置过期时间")
  public void should_() throws InterruptedException {

    String key = "GuanYu";
    User user = User.builder().id(1001).name(key).build();
    setCache(key, gson.toJson(user));
    log.info("添加缓存");

    redisTemplate.expire(key, 1, TimeUnit.SECONDS);

    Assertions.assertThat(redisTemplate.hasKey(key)).isTrue();
    Thread.sleep(3000);
    Assertions.assertThat(redisTemplate.hasKey(key)).isFalse();
  }

  @Test
  @DisplayName("添加一个对象缓存并设置过期时间")
  public void testRedisTemplateObj_2() {
    String key = "LiuBei";
    User user = User.builder().id(1002).name(key).build();
    setCacheWithExpire(key, gson.toJson(user), 1L);

    Assertions.assertThat(user).isEqualTo(gson.fromJson(getCache(key).toString(), type));
    Assertions.assertThat(deleteCache(key)).isTrue();
  }

  // region StringRedisTemplate

  @Test
  @DisplayName("redis测试")
  public void testStringRedisTemplate() {
    redisTemplate
        .opsForValue()
        .set("test_string", "redis value", REDIS_KEEP_TIME, TimeUnit.MINUTES);
  }
  // end

  /**
   * 设置缓存
   *
   * @param key
   * @param value
   */
  public void setCache(String key, String value) {
    redisTemplate.opsForValue().set(key, value);
    System.out.println("当前存储键值对为=>key=" + key + ",value=" + value);
  }
  // endregion

  /**
   * 设置缓存并设定过期时间
   *
   * @param key
   * @param value
   * @param time
   */
  public void setCacheWithExpire(String key, String value, long time) {
    redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    System.out.println("当前存储键值对为=>key=" + key + ",value=" + value);
  }

  /**
   * 获取缓存
   *
   * @param key
   * @return
   */
  public Object getCache(String key) {
    Object value = redisTemplate.opsForValue().get(key);
    System.out.println("key=" + key + ",value=" + value.toString());
    return value;
  }

  /**
   * 根据key删除缓存
   *
   * @param key
   * @return
   */
  public boolean deleteCache(String key) {
    boolean deleted = Boolean.TRUE.equals(redisTemplate.delete(key));
    if (deleted) {
      log.info(String.format("%s,缓存删除成功", key));
    }
    return deleted;
  }

  /**
   * 根据Key设置缓存过期时间
   *
   * @param key
   * @param time
   */
  public void setExpire(String key, int time) {
    redisTemplate.expire(key, time, TimeUnit.MINUTES);
  }

  @Test
  @DisplayName("分布式锁")
  void test_lock() {
    String key = "redisKey";

    System.out.println(redisTemplate.opsForValue().get(key));
    boolean has = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "666"));
    System.out.println("第一次redisKey没有值，则：" + has);

    System.out.println(redisTemplate.opsForValue().get(key));
    has = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, "999"));
    System.out.println("第二次redisKey有值，则：" + has);

    System.out.println(redisTemplate.opsForValue().get(key));

    if (!has) {
      Object redisKey = redisTemplate.opsForValue().getAndSet(key, "000");
      System.out.println(redisKey);
      System.out.println(redisTemplate.opsForValue().get(key));
    }
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  static class User {
    private Integer id;
    private String name;
  }
}
