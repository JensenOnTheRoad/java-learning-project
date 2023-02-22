package basic.data_type;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestString {

  @Test
  @DisplayName("字符串地址")
  void test_same_address() {
    String a = "abc";
    String b = "abc";

    // System.identityHashCode 获取对象的地址
    log.info("a = " + System.identityHashCode(a));
    log.info("b = " + System.identityHashCode(b));

    // 地址相同
    Assertions.assertThat(a == b).isTrue();
  }

  @Test
  @DisplayName("+运算符后的产生新的对象")
  void test_plus() {
    String str1 = "a";
    String str2 = "bc";
    String str3 = "abc";

    // 内存地址不同
    Assertions.assertThat(str1 + str2 == str3).isFalse();

    // 值相同
    Assertions.assertThat((str1 + str2).equals(str3)).isTrue();
  }
}
