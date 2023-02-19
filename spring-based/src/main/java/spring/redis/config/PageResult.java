package spring.redis.config;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author senreysong
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
  private Integer page;
  private Integer size;
  private Integer total;
  private List<T> list;

  public static <T> PageResult<T> of(Integer current, Integer size, Long total, List<T> collect) {
    return new PageResult<>(current, size, total.intValue(), collect);
  }

  public static <T> PageResult<T> of(
      Integer current, Integer size, Integer total, List<T> collect) {
    return new PageResult<>(current, size, total, collect);
  }

  public <R> PageResult<R> map(Function<T, R> map) {
    return new PageResult<>(page, size, total, convertContent(map));
  }

  private <R> List<R> convertContent(Function<T, R> map) {
    return this.getList().stream().map(map).collect(Collectors.toList());
  }

  public static <T> PageResult<T> empty() {
    return new PageResult<>(0, 0, 0, Collections.emptyList());
  }
}
