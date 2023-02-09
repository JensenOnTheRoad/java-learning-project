package number;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestBigDecimal {

  @Test
  @DisplayName("两个BigDecimal比较")
  void compare() {

    // greater than 1
    Assertions.assertThat(BigDecimal.ONE.compareTo(BigDecimal.ZERO)).isEqualTo(1);

    // equal 0
    Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ZERO)).isEqualTo(0);

    // less than -1
    Assertions.assertThat(BigDecimal.ZERO.compareTo(BigDecimal.ONE)).isEqualTo(-1);
  }

  @Test
  @DisplayName("BigDecimal除法若结果包含浮点数，必须手动设置精度和舍入方式。否则回报错")
  void should_test_big_decimal_divide() {
    BigDecimal value1 = BigDecimal.valueOf(10);
    BigDecimal value2 = BigDecimal.valueOf(3);

    assertThrows(ArithmeticException.class, () -> value1.divide(value2));

    // 保留两位小数，四舍五入
    assertDoesNotThrow(
        () -> {
          BigDecimal divide = value1.divide(value2, 2, RoundingMode.HALF_UP);
          Assertions.assertThat(divide.equals(new BigDecimal("3.33"))).isTrue();
        });
  }

  @Test
  @DisplayName("BigDecimal三种toString方式")
  void should_big_decimal_to_String() {

    BigDecimal bigDecimal = new BigDecimal("1E11");

    // 有必要时科学计数法
    log.info(bigDecimal.toString());

    // 工程计数法
    log.info(bigDecimal.toEngineeringString());

    // 不使用任何指数
    log.info(bigDecimal.toPlainString());
  }
}
