package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.Builder;
import org.junit.jupiter.api.Test;

public class BeanUtil {
  private BeanUtil() {}

  public static <T, R> Map<R, T> beanToMap(List<T> list, Function<T, R> apply) {
    HashMap<R, T> map = new HashMap<>(list.size());
    list.forEach(i -> map.put(apply.apply(i), i));
    return map;
  }

  @Builder
  record Result(String code, String desc) {}

  @Test
  void test() {
    List<Result> list =
        List.of(
            Result.builder().code("1").desc("one").build(),
            Result.builder().code("2").desc("two").build());
    Map<String, Result> map = BeanUtil.beanToMap(list, Result::code);
    System.out.println(map);
  }
}
