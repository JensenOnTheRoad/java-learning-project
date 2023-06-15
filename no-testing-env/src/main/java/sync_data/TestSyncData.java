package sync_data;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestSyncData {
  private static final Integer TOTAL_NUMBER = 1000 * 1000;
  private static final Integer PAGE_SIZE = 10;
  private static final Integer PAGE_INDEX = 0;
  private static final Integer FIXED_THREAD_NUMBER = Runtime.getRuntime().availableProcessors();

  /**
   * 单元测试是不支持多线程的，主线程结束之后，不管子线程有没有结束，都会强制退出。
   *
   * <p>但是我们可以通过控制主线程结束的时间来做多线程测试.
   *
   * <p>在JUnit的@Test方法中启用多线程，新启动的线程会随着@Test主线程的死亡而死亡！导致没有任何输出
   *
   * <p>解决方法：
   *
   * <p>在@Test方法中每创建一个线程，就join一下，这样我们新建的线程不死亡，Test主线程也不会死亡。
   *
   * <p>通过主线程休眠足够长的时间来等待子线程执行完，这里需要控制好主线程休眠时间才行。
   *
   * <p>通过CountDownLatch来等待所有子线程执行完毕，才结束主线程。
   */
  @Test
  @DisplayName("多线程 305s")
  void test_sync_through_multi_threading() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    syncDataThroughMultiThead(TOTAL_NUMBER, PAGE_SIZE); // CountDownLatch在此方法内部

    stopWatch.stop();
    log.info("Total spend time: {}s", stopWatch.getTotalTimeSeconds());
  }

  @Test
  @DisplayName("单线程")
  void test_sync() {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    syncData(PAGE_INDEX, PAGE_SIZE);

    stopWatch.stop();
    log.info("Total spend time: {}s", stopWatch.getTotalTimeSeconds());
  }

  // region 多线程方式

  /**
   * 【多线程方式】数据
   *
   * @param total 数据总量
   * @param pageSize 页码
   */
  @SneakyThrows
  public static void syncDataThroughMultiThead(Integer total, Integer pageSize) {
    // 创建线程池
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(FIXED_THREAD_NUMBER);

    // 计算需要同步几次
    int times = total / pageSize;
    if (total % pageSize != 0) {
      times = times + 1;
    }

    CountDownLatch countDownLatch = new CountDownLatch(times);

    // 定义原子变量 - 页数
    AtomicInteger pageIndex = new AtomicInteger(0);
    Runnable task =
        () -> {
          multiFetchAndSaveDatabase(pageIndex.incrementAndGet(), pageSize);
          countDownLatch.countDown();
        };

    // 提交到线程池
    for (int i = 1; i <= times; i++) {
      fixedThreadPool.submit(task);
    }

    // 阻塞等待线程池任务执行完
    countDownLatch.await();

    // 关闭线程池
    if (countDownLatch.getCount() == 0) {
      shutdownAndAwaitTermination(fixedThreadPool);
    }
  }

  /**
   * 多获取并保存数据库 数据同步
   *
   * @param pageIndex 页面索引
   * @param pageSize 页面大小
   */
  private static void multiFetchAndSaveDatabase(int pageIndex, int pageSize) {
    log.info("【数据同步 - 存量】，第{}页开始同步", pageIndex);
    List<Integer> data = mockGetData(pageIndex, pageSize);

    if (!CollectionUtils.isEmpty(data)) {
      mockSaveToDatabase(data);
      log.info("【数据同步 - 存量】，第{}页同步,同步成功", pageIndex);

      if (data.size() < pageSize) {
        log.info("【数据同步 - 存量】,第{}页同步,获取数据小于每页获取条数,证明已全部同步完毕!", pageIndex);
      }

    } else {
      log.info("【数据同步 - 存量】,第{}页同步,获取数据为空,证明已全部同步完毕!!!", pageIndex);
    }
  }
  /** 线程池关闭并等待终止 */
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
  // endregion

  // region 单线程方式
  @SneakyThrows
  public static void syncData(Integer pageIndex, Integer pageSize) {
    while (true) {

      log.info("【数据同步 - 存量】，第{}次同步,", pageIndex);
      List<Integer> data = mockGetData(pageIndex, pageSize);

      // 当获取的数据不为空，且数据量不小于页大小
      if (!CollectionUtils.isEmpty(data) && pageSize <= data.size()) {
        mockSaveToDatabase(data);
        log.info("【数据同步 - 存量】，第{}次同步,同步成功", pageIndex);

        pageIndex += 1;
      } else {
        log.info("【数据同步 - 存量】,第{}次同步,获取数据为空或数据量小于页大小,证明已全部同步完毕!!!", pageIndex);
        break;
      }
    }
  }

  // endregion

  // region mock

  /** 模拟保存到数据库 */
  @SneakyThrows
  private static void mockSaveToDatabase(Collection<?> data) {
    // TODO save to Database
    Thread.sleep(10);
  }

  /**
   * 模拟从第三方得到数据
   *
   * @param pageIndex 页面索引
   * @param pageSize 页面大小
   * @return {@link List}<{@link Integer}>
   */
  @SneakyThrows
  private static List<Integer> mockGetData(Integer pageIndex, Integer pageSize) {
    List<Integer> result = IntStream.rangeClosed(1, pageSize).boxed().toList();

    // 模拟只同步指定页数就返回最后一页的数据
    int times = TOTAL_NUMBER / PAGE_SIZE;
    if (pageIndex.equals(times)) {
      return IntStream.rangeClosed(1, pageSize - 1).boxed().toList();
    }
    Thread.sleep(10);

    return result;
  }
  // endregion
}
