package basic.concurrent.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

public class DemoReentrantLock {

  @Test
  public void test_1() {
    ReentrantLock lock = new ReentrantLock();
    // 获取锁
    lock.lock();
    try {
      int index = 0;
      while (index < 10000) {
        index++;
      }
    } finally {
      // 释放锁
      lock.unlock();
    }
  }

  @Test
  public synchronized void sync() {
    int index = 0;
    while (index++ < 10000) {
      index++;
    }
  }

  // region 功能一：指定公平策略

  /**
   * 在公平锁ReentrantLock构造函数中指定公平策略
   *
   * <p>分别测试为true和为false的输出。
   *
   * <p>为true则输出顺序一定是A B C 但是为false的话有可能输出A C B
   */
  static class FairStrategy {

    private static final Boolean fair = Boolean.FALSE;
    private static final ReentrantLock reentrantLock = new ReentrantLock(fair);

    public static void main(String[] args) throws InterruptedException {
      Thread a = new Thread(() -> test(), "A");
      Thread b = new Thread(() -> test(), "B");
      Thread c = new Thread(() -> test(), "C");
      a.start();
      b.start();
      c.start();
    }

    public static void test() {
      reentrantLock.lock();
      try {
        System.out.println("线程" + Thread.currentThread().getName());
      } finally {
        // 一定要释放锁
        reentrantLock.unlock();
      }
    }
  }
  // endregion

  // region 功能二：非阻塞地获取锁
  private static final ReentrantLock reentrantLock = new ReentrantLock();

  @Data
  @Builder
  static class Account {

    private int balance;

    public Lock getLock() {
      return reentrantLock;
    }
  }

  @Slf4j
  static class NonBlockingAcquireLock {

    public static void main(String[] args) {
      //      sync();
      reentrantLock();
    }

    private static void sync() {
      Account source = Account.builder().balance(10000).build();
      Account target = Account.builder().balance(0).build();

      Thread threadA =
          new Thread(
              () -> {
                transfer_sync(source, target, 100);
              },
              "A");
      Thread threadB =
          new Thread(
              () -> {
                transfer_sync(source, target, 100);
              },
              "B");

      threadA.start();
      threadB.start();
    }

    private static void reentrantLock() {
      Account source = Account.builder().balance(10000).build();
      Account target = Account.builder().balance(0).build();

      Thread threadA =
          new Thread(
              () -> {
                try {
                  transferLock(source, target, 100);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
              },
              "A");

      Thread threadB =
          new Thread(
              () -> {
                try {
                  transferLock(source, target, 100);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
              },
              "B");

      threadA.start();
      threadB.start();
    }

    // region sync
    /*
     * 锁定转入账户
     * Thread1需要获取到B,可是被Thread2锁定了。
     * Thread2需要获取到A，可是被Thread1锁定了。
     * 所以互相等待、死锁
     */
    @SneakyThrows(Exception.class)
    static void transfer_sync(Account source, Account target, int amt) {
      synchronized (source) {
        log.info("\n 线程：{} \n 持有锁: {} \n 等待锁: {}\n", Thread.currentThread(), source, target);
        synchronized (target) {
          if (source.getBalance() > amt) {
            source.setBalance(source.getBalance() - amt);
            target.setBalance(target.getBalance() + amt);
          }
        }
      }
    }
    // endregion

    //  region reentrant lock

    static void transferLock(Account source, Account target, int amount)
        throws InterruptedException {
      boolean isContinue = true;
      while (isContinue) {
        if (source.getLock().tryLock()) {
          log.info("\n {}已获取锁 time:{}\n ", source.getLock(), System.currentTimeMillis());
          try {
            if (target.getLock().tryLock()) {
              log.info("\n {}已获取锁 time:{}\n ", target.getLock(), System.currentTimeMillis());
              try {
                log.info("\n 开始转账操作\n ");
                source.setBalance(source.getBalance() - amount);
                target.setBalance(target.getBalance() + amount);
                log.info(
                    "\n 结束转账操作 source:{} target:{}\n ", source.getBalance(), target.getBalance());
                isContinue = false;
              } finally {
                log.info("\n {}释放锁 time:{}\n ", target.getLock(), System.currentTimeMillis());
                target.getLock().unlock();
              }
            }
          } finally {
            log.info("\n {}释放锁 time:{}\n ", source.getLock(), System.currentTimeMillis());
            source.getLock().unlock();
          }
        }
      }
    }
    // endregion
  }

  // endregion
}
