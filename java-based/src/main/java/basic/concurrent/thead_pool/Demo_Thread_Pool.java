package basic.concurrent.thead_pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jensen_deng
 */
@Slf4j
public class Demo_Thread_Pool {
  public static final Runnable TASK =
      () -> {
        for (int i = 0; i < 20; i++) {
          log.info(Thread.currentThread().getName() + " 自定义线程任务在执行: " + i);
        }
      };

  public static void main(String[] args) {
    // 通过Executors线程池创建工厂类，使用Runnable创建线程池
    //    createThreadPool_Runnable();

    // 通过Executors线程池创建工厂类，使用Callable创建线程池
    //    createThreadPool_Callable();

    manuallyCreateThreadPool();
  }

  /** 手动创建线程池（推荐） 使用guava包中的ThreadFactoryBuilder工厂类来构造线程池 */
  private static void manuallyCreateThreadPool() {

    // Guava
    ExecutorService service =
        new ThreadPoolExecutor(
            10,
            10,
            60L,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadFactoryBuilder().setNameFormat("thread-pool-%d").build(),
            new ThreadPoolExecutor.AbortPolicy());

    // 创建任务对象

    // 将任务提交给线程池
    service.submit(TASK);
    // 再将任务提交给线程池
    service.submit(TASK);

    // 关闭线程池
    service.shutdown();
  }

  /** 通过Executors线程池创建工厂类，使用Runnable创建线程池 */
  private static void createThreadPool_Runnable() {
    /* 只有一个线程的线程池 */
    //    ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /* 不设置上线的线程池，任何提交的任务都将立即执行 */
    //    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /* 固定大小的线程池 */
    ExecutorService service = Executors.newFixedThreadPool(5);

    // 创建任务对象
    Runnable task =
        () -> {
          for (int i = 0; i < 20; i++) {
            log.info(Thread.currentThread().getName() + " 自定义线程任务在执行: " + i);
          }
        };

    // 将任务提交给线程池
    service.submit(task);
    // 再将任务提交给线程池
    service.submit(task);

    // 关闭线程池
    service.shutdown();
  }

  /** 通过Executors线程池创建工厂类，使用Callable创建线程池 */
  private static void createThreadPool_Callable() {
    /* 固定大小的线程池 */
    ExecutorService service = Executors.newFixedThreadPool(5);
    // 创建任务对象
    Callable<Object> task =
        () -> {
          for (int i = 0; i < 20; i++) {
            log.info(Thread.currentThread().getName() + " 自定义线程任务在执行: " + i);
          }
          return null;
        };

    // 将任务提交给线程池
    service.submit(task);
    // 再将任务提交给线程池
    service.submit(task);

    // 关闭线程池
    service.shutdown();
  }
}
