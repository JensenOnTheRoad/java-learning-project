package basic.concurrent.lock;

import java.util.concurrent.locks.ReentrantLock;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Demo_Lock {

  public static void main(String[] args) {
    //    ReentrantLockTest.test_lock();
    //    ReentrantLockTest.test_not_lock();

    SynchronizedTest.test_sync();
//    SynchronizedTest.test_un_sync();
  }

  static class SynchronizedTest {
    static Integer TICKET = 20;

    static synchronized void test() {
      while (TICKET >= 0) {
        log.info(Thread.currentThread().getName() + " sell ticket : " + TICKET--);
      }
    }

    static void test_1() {
      while (TICKET >= 0) {
        log.info(Thread.currentThread().getName() + " sell ticket : " + TICKET--);
      }
    }

    static Runnable runnable_1 = SynchronizedTest::test;

    public static void test_sync() {
      Thread thread_1 = new Thread(runnable_1);
      thread_1.start();

      Thread thread_2 = new Thread(runnable_1);
      thread_2.start();

      Thread thread_3 = new Thread(runnable_1);
      thread_3.start();
    }

    static Runnable runnable_2 = SynchronizedTest::test_1;

    public static void test_un_sync() {
      Thread thread_1 = new Thread(runnable_2);
      thread_1.start();

      Thread thread_2 = new Thread(runnable_2);
      thread_2.start();

      Thread thread_3 = new Thread(runnable_2);
      thread_3.start();
    }
  }

  static class ReentrantLockTest {
    static Integer TICKET = 20;
    static ReentrantLock lock = new ReentrantLock();
    static Runnable runnable_1 =
        () -> {
          while (TICKET > 1) {
            lock.lock();
            log.info(Thread.currentThread().getName() + " sell ticket : " + TICKET);
            TICKET--;
            lock.unlock();
          }
        };

    static Runnable runnable_2 =
        () -> {
          while (TICKET > 1) {
            log.info(Thread.currentThread().getName() + " sell ticket : " + TICKET);
            TICKET--;
          }
        };

    public static void test_lock() {
      Thread thread_1 = new Thread(runnable_1);
      thread_1.start();

      Thread thread_2 = new Thread(runnable_1);
      thread_2.start();

      Thread thread_3 = new Thread(runnable_1);
      thread_3.start();
    }

    public static void test_not_lock() {
      Thread thread_1 = new Thread(runnable_2);
      thread_1.start();

      Thread thread_2 = new Thread(runnable_2);
      thread_2.start();

      Thread thread_3 = new Thread(runnable_2);
      thread_3.start();
    }
  }
}
