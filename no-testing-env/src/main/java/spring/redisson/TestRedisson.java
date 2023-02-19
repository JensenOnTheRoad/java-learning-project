package spring.redisson;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jensen_deng
 */
@Slf4j
@RestController
@RequestMapping("/redisson")
public class TestRedisson {

  public static final String RW_LOCK = "rwLock";
  @Autowired RedissonClient redisson;

  // region 分布式锁

  @GetMapping("/lock")
  public void test() throws InterruptedException {
    // 获取锁
    RLock lock = redisson.getLock("lock");

    /*
    获取锁参数：
      获取锁的最大等待时间(期间会重试)
      锁自动释放时间
      时间单位
     */
    boolean isLock = lock.tryLock(1, 10, TimeUnit.SECONDS);

    if (isLock) {
      try {
        log.info("获取成功，线程ID：{}", Thread.currentThread().getId());
        Thread.sleep(10000);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      } finally {
        // 释放锁
        log.info("解锁成功，线程ID：{}", Thread.currentThread().getId());
        lock.unlock();
      }
    }
  }

  // endregion

  // region 读写锁
  /*
   读写锁 读读不会阻塞，其他情况阻塞

   127.0.0.1:8080/redisson/lock/write
   127.0.0.1:8080/redisson/lock/read
  */
  @GetMapping("/lock/read")
  public void readLock() throws InterruptedException {
    RReadWriteLock lock = redisson.getReadWriteLock(RW_LOCK);
    RLock readLock = lock.readLock();

    boolean tryLock = readLock.tryLock(1, 60, TimeUnit.SECONDS);
    if (tryLock) {
      try {
        log.info("获取成功，线程ID：{}", Thread.currentThread().getId());
        Thread.sleep(6000);
      } finally {
        readLock.unlock();
        log.info("解锁成功，线程ID：{}", Thread.currentThread().getId());
      }
    } else {
      log.warn("无法获取锁，线程ID：{}", Thread.currentThread().getId());
      throw new InterruptedException("无法获取锁");
    }
  }

  @GetMapping("/lock/write")
  public void writeLock() throws InterruptedException {
    RReadWriteLock lock = redisson.getReadWriteLock(RW_LOCK);
    RLock writeLock = lock.writeLock();

    boolean tryLock = writeLock.tryLock(1, 60, TimeUnit.SECONDS);
    if (tryLock) {
      try {
        log.info("获取成功，线程ID：{}", Thread.currentThread().getId());
        Thread.sleep(6000);
      } finally {
        writeLock.unlock();
        log.info("解锁成功，线程ID：{}", Thread.currentThread().getId());
      }
    } else {
      log.warn("无法获取锁，线程ID：{}", Thread.currentThread().getId());
      throw new InterruptedException("无法获取锁");
    }
  }
  // endregion
}
