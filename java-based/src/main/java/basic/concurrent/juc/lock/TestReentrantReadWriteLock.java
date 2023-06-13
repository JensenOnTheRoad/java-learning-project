package basic.concurrent.juc.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jensen
 */
@Slf4j
public class TestReentrantReadWriteLock {
  private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
  private static double SHARE_DATA = 0;

  public static void main(String[] args) throws InterruptedException {
    ExecutorService pool = Executors.newCachedThreadPool();

    Runnable runnableRead =
        () -> {
          ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

          readLock.lock();
          log.info("读数据：{}", SHARE_DATA);
          readLock.unlock();
        };

    Runnable runnableWrite =
        () -> {
          ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

          if (!writeLock.tryLock()) {
            log.info(
                "{} try get write lock, but currently blocked waiting.",
                Thread.currentThread().getName());
          }

          SHARE_DATA = new Random().nextInt(10);
          log.info("写数据：{} ", SHARE_DATA);

          writeLock.unlock();
          log.info("{} release lock.", Thread.currentThread().getName());
        };

    for (int i = 1; i < 10; i++) {
      pool.submit(runnableRead);
      pool.submit(runnableRead);
      pool.submit(runnableWrite);
      pool.submit(runnableWrite);
      Thread.sleep(1000);
    }

    pool.shutdown();
  }
}
