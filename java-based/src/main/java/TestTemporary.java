import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.util.Strings;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;

/**
 * @author jensen_deng
 */
public class TestTemporary {

  @Test
  @DisplayName("获得当前系统信息")
  public void test() {
    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    File file = new File(System.getProperty("user.home"));
    System.out.println("file.getAbsoluteFile() = " + file.getAbsoluteFile());

    System.out.println(
        "file.getAbsoluteFile() = " + new File(System.getProperty("user.dir")).getAbsoluteFile());
  }

  @Test
  void test_1231() {

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime expireTime = LocalDateTime.of(2022, 12, 17, 1, 1);
    long days = 0L;
    days = Duration.between(expireTime, now).toDays();
    System.out.println(days);
  }

  @Test
  void name_11111() {
    Integer count = 20;
    Integer stock = 10;
    System.out.println(stock.compareTo(count));
  }

  @Test
  void test__111() {
    Long count = 1L;
  }

  @Test
  @DisplayName("列表为空时")
  void should_1() {
    ArrayList<Integer> arrayList = new ArrayList<>();
    ArrayList<String> list = null;

    System.out.println(CollectionUtils.isEmpty(arrayList));
    System.out.println(CollectionUtils.isEmpty(list));
    System.out.println(Optional.ofNullable(arrayList).isEmpty());
  }

  @Test
  @DisplayName("Optional类空安全问题")
  void should_npe_of_optional() {
    assertDoesNotThrow(
        () -> {
          Optional.ofNullable(null).isEmpty(); // 不存在空安全问题
        });

    assertThrows(
        NullPointerException.class,
        () -> {
          Optional.of(null).isEmpty(); // 存在空安全问题
        });
  }

  @Test
  @DisplayName("Strings工具类空安全问题")
  void should_npe_of_strings_util() {
    assertDoesNotThrow(
        () -> {
          Strings.isNotEmpty(null); // 不存在空安全问题
        });
  }

  @Test
  @DisplayName("String类空安全问题")
  void should_npe_of_string_class() {
    Integer integer = null;

    assertDoesNotThrow(
        () -> {
          String.valueOf(integer); // 不存在空安全问题
        });
  }

  @Test
  @DisplayName("List类，空安全问题")
  void should_npe_of_collection() {
    ArrayList<Integer> list = null;

    assertThrows(NullPointerException.class, () -> list.isEmpty()); // 存在空安全问题

    assertDoesNotThrow(
        () -> {
          CollectionUtils.isEmpty(list); // 不存在空安全问题
        });
  }

  @Test
  @DisplayName("")
  void should_npe_of_equals() {
    String name = null;
    assertThrows(NullPointerException.class, () -> name.equals("Jonathan")); // 存在空安全问题
    assertDoesNotThrow(
        () -> {
          "Jonathan".equals(name); // 不存在空安全问题
        });
  }

  @Test
  @DisplayName("")
  void should_() {
    Integer i = 100;
    assertTrue(System.identityHashCode(i) == System.identityHashCode(i));
  }

  @Test
  @DisplayName("获取当前JVM内存状态信息")
  void should_memory() {
    long rate = 1024 * 1024;
    // 最大可用内存，对应-Xmx
    long maxMemory = Runtime.getRuntime().maxMemory();
    System.out.printf("maxMemory = %dM%n", maxMemory / rate);

    // 当前JVM空闲内存
    long freeMemory = Runtime.getRuntime().freeMemory();
    System.out.printf("freMemory = %dM%n", freeMemory / rate);

    // 当前JVM占用的内存总数，其值相当于当前JVM已使用的内存及freeMemory()的总和
    long totalMemory = Runtime.getRuntime().totalMemory();
    System.out.printf("totalMemory = %dM%n", totalMemory / rate);

    // 当前使用内存
    System.out.printf("useMemory = %dM%n", (totalMemory - freeMemory) / rate);
  }

  @Test
  @DisplayName("")
  void should_111() {
    int i = (int) Math.sqrt(100000);
    System.out.println(i);
  }

  @Test
  @DisplayName("")
  void should_11221() {
    Object object;

    Long id = 1L;
    object = id;
    if (!(object instanceof Number)) {
    } else {
      System.out.println("b是数字");
    }

    if (object instanceof Number) {
      System.out.println("是数字");
    }
  }

  @Test
  @DisplayName("")
  void should_1123() {

    BigDecimal value = BigDecimal.valueOf(100.131231);

    System.out.println(value.longValue());
  }

  @Test
  @DisplayName("t")
  void should_11111111() {
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    Integer[] integers = list.toArray(new Integer[0]);

    for (Integer integer : integers) {
      System.out.println(integer);
    }

    List<Integer> list1 = null;
    Assertions.assertThatThrownBy(() -> list1.forEach(System.out::println))
        .isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  @DisplayName("")
  void should_11111() {
    TestClass t = new TestClass();
    System.out.println(t.ints);
  }

  static class TestClass {
    int[] ints;
  }
}
