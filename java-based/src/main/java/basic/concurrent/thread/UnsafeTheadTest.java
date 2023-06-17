package basic.concurrent.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 1000个线程对count进行自增操作，操作结束后值可能小于1000
 *
 * <p>++ 线程不安全
 *
 * @param args
 * @throws InterruptedException
 */
public class UnsafeTheadTest {

  private static final int COUNT = 1000;
  public static final int THREADS = Runtime.getRuntime().availableProcessors();

  @SneakyThrows
  @Test
  @DisplayName("++线程不安全")
  void test() {
    ThreadUnsafeExample example = new ThreadUnsafeExample();

    // 多线程倒计时计数器初，始化后计数器值递减到0的时候，不能再复原的
    final CountDownLatch countDownLatch = new CountDownLatch(COUNT);

    // 创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    for (int i = 0; i < COUNT; i++) {
      executorService.execute(
          () -> {
            example.add();
            countDownLatch.countDown(); // 该线程执行完毕-1
          });
    }

    // await()方法的线程会被挂起，等待直到count值为0再继续执行。
    countDownLatch.await();
    executorService.shutdown();

    // 结果总是小于1000
    Assertions.assertThat(example.get() < 1000).isTrue();
  }

  @SneakyThrows
  @Test
  @DisplayName("线程安全")
  void test_safe() {
    ThreadSafeExample example = new ThreadSafeExample();

    // 多线程倒计时计数器初，始化后计数器值递减到0的时候，不能再复原的
    final CountDownLatch countDownLatch = new CountDownLatch(COUNT);

    // 创建线程池
    ExecutorService executorService = Executors.newFixedThreadPool(THREADS);

    for (int i = 0; i < COUNT; i++) {
      executorService.execute(
          () -> {
            example.add();
            countDownLatch.countDown(); // 该线程执行完毕-1
          });
    }

    // await()方法的线程会被挂起，等待直到count值为0再继续执行。
    countDownLatch.await();
    executorService.shutdown();

    // 结果总是小于1000
    Assertions.assertThat(example.get() == 1000).isTrue();
  }

  static class ThreadUnsafeExample {
    private Integer count = 0;

    public void add() {
      count++;
    }

    public int get() {
      return count;
    }
  }

  static class ThreadSafeExample {
    private AtomicInteger count = new AtomicInteger(0);

    public void add() {
      count.incrementAndGet();
    }

    public int get() {
      return count.get();
    }
  }
}
