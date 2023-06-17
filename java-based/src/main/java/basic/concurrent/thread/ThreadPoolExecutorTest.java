package basic.concurrent.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/**
 * 线程池
 *
 * @author jensen
 */
@Slf4j
public class ThreadPoolExecutorTest {
  private static final int TASK_COUNT = 11; // 任务数
  private static final int CORE_POOL_SIZE = 10;
  private static final int MAXIMUM_POOL_SIZE = 10;
  private static final int KEEP_ALIVE_TIME = 5;
  private static final ArrayBlockingQueue<Runnable> WORK_QUEUE = new ArrayBlockingQueue<>(10);

  @SneakyThrows
  public static void main(String[] args) {
    AtomicInteger integer = new AtomicInteger();

    ThreadPoolExecutor executor =
        new ThreadPoolExecutor(
            CORE_POOL_SIZE, // 核心线程数
            MAXIMUM_POOL_SIZE, // 最大线程数
            KEEP_ALIVE_TIME, // 非核心回收超时时间
            TimeUnit.SECONDS, // 超时时间单位
            WORK_QUEUE); // 任务队列

    CountDownLatch countDownLatch = new CountDownLatch(TASK_COUNT);

    Runnable task =
        () -> {
          mockExecute();
          countDownLatch.countDown();
          log.info(
              Thread.currentThread().getName() + ":  已执行第" + integer.incrementAndGet() + "个任务");
        };

    StopWatch watch = new StopWatch();
    watch.start();

    // 模拟任务提交
    IntStream.range(0, TASK_COUNT)
        .forEach(
            x -> {
              Thread thread = new Thread(task);

              try {
                executor.execute(thread);
              } catch (RejectedExecutionException e) {
                log.error(e.getMessage());
              }
            });

    executor.shutdown();

    countDownLatch.await();
    watch.stop();

    log.info("total spend time : {}s ", watch.getTotalTimeSeconds());
  }

  @SneakyThrows
  private static void mockExecute() {
    Thread.sleep(500);
  }
}
