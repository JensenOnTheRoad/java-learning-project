package sync_data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * @author jensen_deng
 */
@Slf4j
public class DemoSyncData {

  public static final int TOTAL = 1000; // 数据总量
  public static final int PAGE_SIZE = 10;

  public static void main(String[] args) {
    synAllData(TOTAL, PAGE_SIZE);
  }

  /**
   * 【多线程方式】数据
   *
   * @param total 数据总量
   * @param pageSize 页码
   */
  @SneakyThrows
  public static void synAllData(int total, int pageSize) {

    // 定义原子变量 - 页数
    AtomicInteger pageIndex = new AtomicInteger(0);
    // 创建线程池
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    // 计算需要同步几次
    int times = total / pageSize;
    if (total % pageSize != 0) {
      times = times + 1;
    }

    LocalDateTime beginTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    log.info("【数据同步 - 存量】开始同步时间：{}", beginTime.format(formatter));

    Callable<Integer> callable =
        () -> {
          int index = pageIndex.incrementAndGet();
          try {
            multiFetchAndSaveDB(index, pageSize);
          } catch (Exception e) {
            log.error("并发获取并保存数据异常：", e);
          }
          return index;
        };

    // 提交到线程池
    for (int i = 1; i <= times; i++) {
      Future<Integer> task = fixedThreadPool.submit(callable);

      // 判断是否完成
      if (task.get().equals(times) && task.isDone()) {
        LocalDateTime endLocalDateTime = LocalDateTime.now();
        log.info(
            "【数据同步 - 存量】同步结束时间：{},总共耗时：{}分钟",
            endLocalDateTime.format(formatter),
            Duration.between(beginTime, endLocalDateTime).toMinutes());
        fixedThreadPool.shutdown();
      }
    }
  }

  /**
   * 多获取并保存数据库 数据同步
   *
   * @param pageIndex 页面索引
   * @param pageSize 页面大小
   */
  private static void multiFetchAndSaveDB(int pageIndex, int pageSize) {
    log.info("【数据同步 - 存量】，第{}页同步,", pageIndex);
    List<Integer> data = mockGetData(pageIndex, pageSize);

    if (!CollectionUtils.isEmpty(data)) {
      log.info("【数据同步 - 存量】，第{}页同步,同步成功", pageIndex);
      // TODO save to Database
      if (data.size() < pageSize) {
        log.info("【数据同步 - 存量】,第{}页同步,获取数据小于每页获取条数,证明已全部同步完毕!!!", pageIndex);
      }
    } else {
      log.info("【数据同步 - 存量】,第{}页同步,获取数据为空,证明已全部同步完毕!!!", pageIndex);
    }
  }

  /**
   * 模拟得到数据
   *
   * @param pageIndex 页面索引
   * @param pageSize 页面大小
   * @return {@link List}<{@link Integer}>
   */
  private static List<Integer> mockGetData(int pageIndex, int pageSize) {
    return IntStream.rangeClosed(1, 10).boxed().toList();
  }
}
