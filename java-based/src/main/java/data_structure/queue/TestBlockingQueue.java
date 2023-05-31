package data_structure.queue;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * 阻塞测试 当 ArrayBlockingQueue 队列满了之后就会进入阻塞，当过了 1 秒有元素从队列中移除之后，才会将新的元素入列。
 *
 * @author jensen_deng
 * @date 2023/05/31
 */
@Slf4j
public class TestBlockingQueue {
  public static void main(String[] args) {
    // 创建一个长度为 5 的阻塞队列
    ArrayBlockingQueue<Object> queue = new ArrayBlockingQueue<>(5);

    // 新创建一个线程执行入列
    new Thread(
            () -> {
              // 循环 10 次
              for (int i = 0; i < 10; i++) {
                try {
                  queue.put(i);
                } catch (InterruptedException e) {
                  throw new RuntimeException(e);
                }
                log.info(
                    LocalDateTime.now() + " |  into  |ArrayBlockingQueue Size:" + queue.size());
              }
              log.info(LocalDateTime.now() + " | For End.");
            })
        .start();

    // 新创建一个线程执行出列
    new Thread(
            () -> {
              for (int i = 0; i < 5; i++) {
                try {
                  // 休眠 1S
                  Thread.sleep(1000);

                  if (!queue.isEmpty()) {
                    queue.take(); // 出列
                    log.info(
                        LocalDateTime.now() + " | output |ArrayBlockingQueue Size:" + queue.size());
                  }
                } catch (InterruptedException e) {
                  log.error("", e);
                }
              }
            })
        .start();
  }
}
