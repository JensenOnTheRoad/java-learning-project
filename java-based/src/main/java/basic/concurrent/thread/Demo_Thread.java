package basic.concurrent.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class Demo_Thread {

  /** yield线程让步使用JUnit进行测试可能会使得线程让步后不会再分配到CPU资源，所以使用只能使用main进行测试。 */
  public static void main(String[] args) {
    yieldTest(true); // 使用线程让步
    yieldTest(false); // 不使用线程让步
  }

  private static void yieldTest(boolean isYield) {
    Thread thread;
    if (isYield) {
      thread =
          new Thread(
              () -> {
                long startTime = System.currentTimeMillis();
                long count = 0;
                for (int i = 0; i < 500000; i++) {
                  count = count + i;
                  Thread.yield();
                }
                long endTime = System.currentTimeMillis();
                log.info("yield 方法的使用，包含总用时: " + (endTime - startTime) + " 毫秒");
              });
    } else {
      thread =
          new Thread(
              () -> {
                long startTime = System.currentTimeMillis();
                long count = 0;
                for (int i = 0; i < 500000; i++) {
                  count = count + i;
                }
                long endTime = System.currentTimeMillis();
                log.info("yield 方法的使用，包含总用时: " + (endTime - startTime) + " 毫秒");
              });
    }
    thread.start();
  }

  @Test
  @DisplayName("Thread Test,使用内部类继承Thread，开启三个线程，线程各自执行内容")
  public void test_() {
    Demo_Thread thread = new Demo_Thread();
    // 每次都创建一个对象，所以是三个独立的线程
    thread.new MyThread().start();
    thread.new MyThread().start();
    thread.new MyThread().start();
  }

  class MyThread extends Thread {
    private int ticket = 20;

    public MyThread() {}

    @Override
    public void run() {
      for (int i = 0; i <= 20; i++) {
        if (ticket > 0) {
          System.out.println(Thread.currentThread().getName() + " sell ticket : " + ticket--);
        }
      }
    }
  }
}
