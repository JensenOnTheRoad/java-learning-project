package spring.real_time_task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;

/**
 * <a href="https://stackoverflow.com/questions/39737013/spring-boot-best-way-to-start-a-background-thread-on-deployment">...</a>
 *
 * <p>实时任务，通过DisposableBean和Runnable实现
 *
 * @author jensen_deng
 * @date 2023/01/03
 */
//@Component
@Slf4j
public class RealTimeImpl1 implements DisposableBean, Runnable {

  private volatile boolean condition = Boolean.TRUE;

  RealTimeImpl1() {
    Thread thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {
    while (condition) {
      task();
    }
  }

  @Override
  public void destroy() {
    condition = false;
  }

  @SneakyThrows
  private void task() {
    Thread.sleep(5000);
    log.info("Real Time Implement For 'DisposableBean And Runnable'");
  }
}
