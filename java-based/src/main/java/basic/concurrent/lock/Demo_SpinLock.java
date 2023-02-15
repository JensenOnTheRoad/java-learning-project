package basic.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写自旋锁
 *
 * <p>AtomicReference中的初始值一定为null，
 *
 * <p>所以第一个线程在调用lock()方法后会成功将当前线程的对象放入AtomicReference，
 *
 * <p>此时若是别的线程调用lock()方法，会因为该线程对象与AtomicReference中的对象不同而陷入循环的等待中，
 *
 * <p>直到第一个线程执行完++操作，调用了unlock()方法，该线程才会将AtomicReference值置为null
 *
 * <p>，此时别的线程就可以跳出循环了。
 *
 * @author jensen_deng
 */
public class Demo_SpinLock {

  private final AtomicReference<Thread> atomicReference = new AtomicReference<>();

  public void lock() {
    // 获取当前线程对象
    Thread thread = Thread.currentThread();
    // 自旋等待
    while (!atomicReference.compareAndSet(null, thread)) {}
  }

  public void unlock() {
    Thread thread = Thread.currentThread();
    atomicReference.compareAndSet(thread, null);
  }

  static int count = 0;

  public static void main(String[] args) throws InterruptedException {
    Demo_SpinLock lock = new Demo_SpinLock();

    List<Thread> threads = new ArrayList<>();

    for (int i = 0; i < 50; i++) {
      Thread thread =
          new Thread(
              () -> {
                lock.lock();
                for (int j = 0; j < 1000; j++) {
                  count++;
                }
                lock.unlock();
              });
      thread.start();
      threads.add(thread);
    }

    for (Thread thread : threads) {
      thread.join();
    }

    System.out.println(count);
  }
}
