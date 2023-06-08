package basic.concurrent.thread.futuretask;

import java.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jensen
 */
@Slf4j
public class AsyncAndWaitTest {

  public static void main(String[] args) throws ExecutionException, InterruptedException {

    ExecutorService executor = Executors.newFixedThreadPool(10);

    Callable<String> callable =
        () -> {
          log.info("{}========>正在执行", Thread.currentThread().getName());
          Thread.sleep(3 * 1000L);
          return "success";
        };

    // FutureTask实现了Runnable，可以看做是一个任务
    FutureTask<String> futureTask = new FutureTask<>(callable);

    log.info(Thread.currentThread().getName() + "========>启动任务");

    // 传入futureTask，执行任务
    executor.submit(futureTask);

    try {
      // 但它同时又实现了Future，可以获取异步结果（会阻塞3秒）
      String result = futureTask.get();
      if (result != null) {
        log.info("Task is finished，result ===> {}", result);
      } else {
        log.info("Task was finished, but no result");
      }
    } finally {
      executor.shutdown();
    }
  }
}
