package basic.concurrent.thread.futuretask;

import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jensen
 */
@Slf4j
public class CallableTest {

  public static void main(String[] args) throws Exception {
    // 创建线程池
    ExecutorService es = Executors.newSingleThreadExecutor();

    // 创建Callable对象任务，重写call方法
    CallableDemo callable = new CallableDemo();

    // 创建FutureTask
    FutureTask<Integer> task = new FutureTask<>(callable);

    // 执行任务
    es.submit(task);
    // 关闭线程池
    es.shutdown();

    try {
      Thread.sleep(2000);
      log.info("主线程在执行其他任务");

      if (task.get() != null) {
        // 输出获取到的结果
        log.info("task.get()-->" + task.get());
      } else {
        // 输出获取到的结果
        log.info("task.get()未获取到结果");
      }

    } catch (Exception e) {
      log.info("Exception:", e);
    }
    log.info("主线程在执行完成");
  }

  @Slf4j
  static class CallableDemo implements Callable<Integer> {
    private int sum;

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Integer call() throws Exception {
      log.info("Callable子线程开始计算啦！");
      Thread.sleep(2000);
      for (int i = 0; i < 5000; i++) {
        sum = sum + i;
      }
      log.info("Callable子线程计算结束！");
      return sum;
    }
  }
}
