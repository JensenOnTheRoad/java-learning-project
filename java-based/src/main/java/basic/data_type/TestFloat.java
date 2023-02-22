package basic.data_type;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestFloat {

  @Test
  void test_float() {
    Assertions.assertThatThrownBy(
            () -> {
              float a = 3.4f;
              log.info(String.valueOf(a));
            })
        .doesNotThrowAnyException();
  }
}
