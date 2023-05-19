package basic.time;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestDateTimeFormat {

  @Test
  @DisplayName("获取当前日期的开始时间和结束时间")
  void test_() {
    LocalDate date = LocalDate.of(2023, 4, 6);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String startTime = LocalDateTime.of(date, LocalTime.MIN).format(formatter);
    String endTime = LocalDateTime.of(date, LocalTime.MAX).format(formatter);

    Assertions.assertThat(startTime).isEqualTo("2023-04-06 00:00:00");
    Assertions.assertThat(endTime).isEqualTo("2023-04-06 23:59:59");
  }

  @Test
  @DisplayName("获取上个月的今天")
  void test_last_month() {
    LocalDate date = LocalDate.now();
    date = date.minusMonths(1);
    log.info(date.toString());
  }

  @Test
  @DisplayName("java1.8以后的 使用LocalDateTime类下的上个月月末时间")
  public void demo2() {
    String date = "2019-03-28 00:00:00";
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime date2 = LocalDateTime.parse(date, fmt);

    LocalDateTime localDateTime = date2.minusMonths(1);
    LocalDateTime with = localDateTime.with(TemporalAdjusters.lastDayOfMonth());

    System.out.println(date);
    System.out.println(with);
  }

  @Test
  @DisplayName("java1.8后 使用LocalDateTime类下的 上个月月初时间")
  public void demo3() {
    String date1 = "2019-03-28 00:00:00";
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime date2 = LocalDateTime.parse(date1, fmt);
    LocalDateTime localDateTime = date2.minusMonths(1);
    LocalDateTime with = localDateTime.with(TemporalAdjusters.firstDayOfMonth());
    System.out.println(date1);
    System.out.println(with);
  }

  // region

  @Test
  @DisplayName("获取当天结束")
  void test_get_day_of_end() {
    String dateStr = "1997-07-01";
    DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatterDatetime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDate date = LocalDate.parse(dateStr, formatterDate);

    LocalDateTime start = date.atStartOfDay();
    log.info(start.format(formatterDatetime));

    LocalDateTime end = LocalDateTime.of(date, LocalTime.MAX);
    log.info(end.format(formatterDatetime));
  }

  // endregion

  @Test
  @DisplayName("获取当月第一天")
  void test_111() {
    LocalDate date = LocalDate.now();
    LocalDate startTime = date.with(TemporalAdjusters.firstDayOfMonth());

    log.info(startTime.toString());
  }
}
