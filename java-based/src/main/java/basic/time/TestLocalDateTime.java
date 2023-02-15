package basic.time;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestLocalDateTime {

  /**
   * {epochSecond} - 从 1970-01-01T00:00:00Z 开始的秒数。
   * <p>
   * {nanoAdjustment} - 纳秒调整秒数，正数或负数。
   */
  @Test
  @DisplayName("")
  void test() {
    ZoneOffset zoneOffset = ZonedDateTime.now().getOffset();
    LocalDateTime time = LocalDateTime.ofEpochSecond(1L, 1, zoneOffset);
    Assertions.assertThat(time).isNotNull();
  }

  @Test
  void test1() {
    String text = "2022-12-30 10:00:00";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime.parse(text, formatter);
  }
}

