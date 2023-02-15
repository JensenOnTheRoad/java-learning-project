package basic.concurrent.runnable;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.extern.slf4j.Slf4j;

/**
 * JUnit不支持多线程，所以只能在main方法中进行测试
 *
 * @author jensen_deng
 */
@Slf4j
public class Demo_Runnable {
  private static int COUNT = 20;
  private static AtomicInteger TICKET = new AtomicInteger(COUNT);
  private static Runnable runnable = // 重写Runnable的run方法
      () -> {
        for (int i = 0; i <= COUNT; i++) {
          if (TICKET.get() > 0) {
            log.info(
                Thread.currentThread().getName() + " sell ticket : " + TICKET.getAndDecrement());
          }
        }
      };

  public static void main(String[] args) throws InterruptedException {

    /** Runnable Test，三个线程共同做一件事情 */
    runnable_test();

    /** 多线程sleep测试 */
    //        sleep_test();

    /** Runnable Test，使用内部类实现Runnable接口，开启三个线程共同做一件事情" */
    //    runnable_inner_class_test();
  }

  public static void runnable_test() {

    log.info("=".repeat(20) + "Runnable Test" + "=".repeat(20));

    // 开启线程并设置线程优先级
    Thread thread_0 = new Thread(runnable);
    thread_0.setPriority(Thread.MAX_PRIORITY); // 最大优先级10
    thread_0.start();

    Thread thread_1 = new Thread(runnable);
    thread_1.setPriority(Thread.NORM_PRIORITY); // 默认优先级5
    thread_1.start();

    Thread thread_2 = new Thread(runnable);
    thread_2.setPriority(Thread.MIN_PRIORITY); // 最低优先级1
    thread_2.start();
  }

  public static void sleep_test() {

    log.info(("=".repeat(20) + "Runnable Sleep Test" + "=".repeat(20)));

    Runnable runnable_while =
        () -> {
          for (int i = 0; i <= 20; i++) {
            log.info(Thread.currentThread().getName() + " : " + i);
            if (i == 10) {
              try {
                log.info("-".repeat(20) + " thread sleep 1s" + "-".repeat(20));
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            }
          }
        };

    Thread thread_1 = new Thread(runnable_while);
    thread_1.start();
  }

  static void runnable_inner_class_test() {
    log.info("=".repeat(20) + "Runnable Inner Class Test " + "=".repeat(20));

    // 开启多线程
    MyRunnable myRunnable = new MyRunnable();
    new Thread(myRunnable).start();
    new Thread(myRunnable).start();
    new Thread(myRunnable).start();
  }

  // 定义内部类实现Runnable接口
  static class MyRunnable implements Runnable {
    private int ticket = 20;

    public MyRunnable() {}

    // 重写Runnable的run方法
    @Override
    public void run() {
      for (int i = 0; i <= 20; i++) {
        if (ticket > 0) {
          log.info(Thread.currentThread().getName() + " sell ticket : " + ticket--);
        }
      }
    }
  }
}
