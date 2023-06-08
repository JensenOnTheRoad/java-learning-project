package basic.concurrent.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 演示多线程测试
 *
 * @author jensen_deng
 * @date 2022/12/05
 */
@Slf4j
// @ExtendWith(ConcurrentTestRunner.class)
public class Demo_MultiThreadTest {

  /** 并发测试 */
  @Execution(ExecutionMode.CONCURRENT)
  @DisplayName("多线程执行10次")
  @RepeatedTest(value = 10, name = "完成度：{currentRepetition}/{totalRepetitions}")
  void concurrentTest(TestInfo testInfo, RepetitionInfo repetitionInfo) {
    log.info(
        "测试方法 [{}]，当前第[{}]次，共[{}]次",
        testInfo.getTestMethod().get().getName(),
        repetitionInfo.getCurrentRepetition(),
        repetitionInfo.getTotalRepetitions());
  }

  /** 参数化测试 */
  @Execution(ExecutionMode.CONCURRENT)
  @DisplayName("多个int型入参")
  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0})
  void intsTest(int candidate) {
    log.info("ints [{}]", candidate);
  }

  @Execution(ExecutionMode.CONCURRENT)
  static class MyTest {

    @Test
    @SneakyThrows
    void test1() {
      Runnable runnable =
          () -> {
            for (int i = 0; i < 1000; i++) {
              log.info("Test1 " + Thread.currentThread().getName());
            }
          };
      runnable.run();
    }

    @Test
    void test2() throws InterruptedException {
      Thread.sleep(1000);
      log.info("Test 2! " + Thread.currentThread().getName());
    }
  }

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
  @SneakyThrows
  public void testMultiThread() {
    CountDownLatch countDownLatch = new CountDownLatch(2);
    Thread thread =
        new Thread(
            () -> {
              try {
                TimeUnit.SECONDS.sleep(1);
                log.info("子线程1执行完毕");
                countDownLatch.countDown();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
    Thread thread2 =
        new Thread(
            () -> {
              try {
                TimeUnit.SECONDS.sleep(2);
                log.info("子线程2执行完毕");
                countDownLatch.countDown();
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            });
    thread.start();
    thread2.start();
    try {
      countDownLatch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    log.info("@Test线程执行完毕");
  }
}
