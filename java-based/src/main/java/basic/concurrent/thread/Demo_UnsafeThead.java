package basic.concurrent.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo_UnsafeThead {

  /**
   * 1000个线程对count进行自增操作，操作结束后值可能小于1000
   *
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {

    ThreadUnsafeExample example = new ThreadUnsafeExample();

    final int theadSize = 1000;

    // 多线程倒计时计数器初，始化后计数器值递减到0的时候，不能再复原的
    final CountDownLatch countDownLatch = new CountDownLatch(theadSize);
    // 创建线程池
    ExecutorService executorService = Executors.newCachedThreadPool();

    for (int i = 0; i < theadSize; i++) {
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
    System.out.println(example.get());
  }

  static class ThreadUnsafeExample {
    private int count = 0;

    public void add() {
      count++;
    }

    public int get() {
      return count;
    }
  }
}
