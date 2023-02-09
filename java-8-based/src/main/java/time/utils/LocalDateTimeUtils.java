package time.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * LocalDateTime解析工具
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LocalDateTimeUtils {

  public static LocalDateTime parseToLocalDateTime(String str, String pattern) {
    if (blankVerify(str, pattern)) {
      return null;
    }

    LocalDateTime localDateTime;

    try {
      localDateTime = LocalDateTime.parse(str, DateTimeFormatter.ofPattern(pattern));
    } catch (Exception ex) {
      ex.printStackTrace();
      LocalDate localDate = parseToLocalDate(str, pattern);
      localDateTime = Objects.isNull(localDate) ? null : localDate.atStartOfDay();
    }
    return localDateTime;
  }

  public static LocalDate parseToLocalDate(String str, String pattern) {
    if (blankVerify(str, pattern)) {
      return null;
    }

    LocalDate localDate = null;

    try {
      localDate = LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern));
    } catch (Exception ex) {
      log.error(ex.getMessage(), ex);
    }
    return localDate;
  }

  private static boolean blankVerify(String str, String pattern) {
    return !StringUtils.hasText(str) || !StringUtils.hasText(pattern);
  }
}
