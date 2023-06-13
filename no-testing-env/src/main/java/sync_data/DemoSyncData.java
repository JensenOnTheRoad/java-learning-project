package sync_data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

/**
 * @author jensen_deng
 */
@Slf4j
public class DemoSyncData {

  public static final Integer TOTAL_NUMBER = 1000 * 1000;
  public static final Integer PAGE_SIZE = 10;
  public static Integer FIXED_THREAD_NUMBER = Runtime.getRuntime().availableProcessors();

  public static void main(String[] args) {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    // 1000 * 1000时，多线程77s，单线程621s

    // 多线程
    syncDataThroughMultiThead(TOTAL_NUMBER, PAGE_SIZE);

    // 单线程
    //    syncData(0, PAGE_SIZE);

    stopWatch.stop();
    log.info("Total spend time: {}", stopWatch.getTotalTimeSeconds());
  }

  // region 多线程方式

  /**
   * 【多线程方式】数据
   *
   * @param total 数据总量
   * @param pageSize 页码
   */
  @SneakyThrows
  public static void syncDataThroughMultiThead(int total, int pageSize) {
    // 定义原子变量 - 页数
    AtomicInteger pageIndex = new AtomicInteger(0);
    // 创建线程池
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(FIXED_THREAD_NUMBER);

    // 计算需要同步几次
    int times = total / pageSize;
    if (total % pageSize != 0) {
      times = times + 1;
    }

    LocalDateTime beginTime = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    log.info("【数据同步 - 存量】开始同步时间：{}", beginTime.format(formatter));

    CountDownLatch countDownLatch = new CountDownLatch(times);

    Callable<Integer> callable =
        () -> {
          int index = pageIndex.incrementAndGet();
          try {
            multiFetchAndSaveDatabase(index, pageSize);
            countDownLatch.countDown();
          } catch (Exception e) {
            log.error("并发获取并保存数据异常：", e);
          }
          return index;
        };

    // 提交到线程池
    for (int i = 1; i <= times; i++) {
      fixedThreadPool.submit(callable);
    }

    // 阻塞等待线程池任务执行完
    countDownLatch.await();

    if (countDownLatch.getCount() == 0) {
      shutdownAndAwaitTermination(fixedThreadPool);
    }
  }

  /**
   * 线程池关闭并等待终止
   *
   * @param pool
   */
  private static void shutdownAndAwaitTermination(ExecutorService pool) {
    // Disable new tasks from being submitted
    pool.shutdown();

    try {
      // Wait a while for existing tasks to terminate
      if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
        // Cancel currently executing tasks
        pool.shutdownNow();

        // Wait a while for tasks to respond to being cancelled
        if (!pool.awaitTermination(30, TimeUnit.SECONDS)) {
          log.error("Pool did not terminate");
        }
      }
    } catch (InterruptedException ie) {
      // (Re-)Cancel if current thread also interrupted
      pool.shutdownNow();
      // Preserve interrupt status
      Thread.currentThread().interrupt();
    } finally {
      if (pool.isTerminated()) {
        log.info("ThreadPool was terminated.");
      }
    }
  }

  /**
   * 多获取并保存数据库 数据同步
   *
   * @param pageIndex 页面索引
   * @param pageSize 页面大小
   */
  private static void multiFetchAndSaveDatabase(int pageIndex, int pageSize) {
    log.info("【数据同步 - 存量】，第{}页同步,", pageIndex);
    List<Integer> data = mockGetData(pageIndex, pageSize);

    if (!CollectionUtils.isEmpty(data)) {

      mockSaveToDB();

      log.info("【数据同步 - 存量】，第{}页同步,同步成功", pageIndex);
      if (data.size() < pageSize) {
        log.info("【数据同步 - 存量】,第{}页同步,获取数据小于每页获取条数,证明已全部同步完毕!!!", pageIndex);
      }
    } else {
      log.info("【数据同步 - 存量】,第{}页同步,获取数据为空,证明已全部同步完毕!!!", pageIndex);
    }
  }

  // endregion

  // region 单线程方式
  @SneakyThrows
  public static void syncData(Integer pageIndex, Integer pageSize) {
    while (true) {

      log.info("【数据同步 - 存量】，第{}次同步,", pageIndex);
      List<Integer> data = mockGetData(pageIndex, pageSize);

      // 当获取的数据不为空，且数据量不小于页大小
      if (!CollectionUtils.isEmpty(data) && pageSize <= data.size()) {
        mockSaveToDB();
        log.info("【数据同步 - 存量】，第{}次同步,同步成功", pageIndex);

        pageIndex += 1;
      } else {
        log.info("【数据同步 - 存量】,第{}次同步,获取数据为空,证明已全部同步完毕!!!", pageIndex);
        break;
      }
    }
  }

  // endregion

  // region mock

  /** 模拟保存到数据库 */
  @SneakyThrows
  private static void mockSaveToDB() {
    // TODO save to Database
    Thread.sleep(5);
  }
  /**
   * 模拟从第三方得到数据
   *
   * @param pageIndex 页面索引
   * @param pageSize 页面大小
   * @return {@link List}<{@link Integer}>
   */
  private static List<Integer> mockGetData(Integer pageIndex, Integer pageSize) {
    List<Integer> result = IntStream.rangeClosed(1, pageSize).boxed().toList();

    // 模拟只同步指定页数就返回最后一页的数据
    int times = TOTAL_NUMBER / PAGE_SIZE;
    if (pageIndex.equals(times)) {
      return IntStream.rangeClosed(1, pageSize - 1).boxed().toList();
    }

    return result;
  }
  // endregion
}
