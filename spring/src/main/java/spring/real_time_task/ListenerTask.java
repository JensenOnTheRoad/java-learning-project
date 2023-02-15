package spring.real_time_task;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ListenerTask implements Runnable {

  @Override
  public void run() {
    task();
  }

  @SneakyThrows
  @Async
  public void task() {
    while (true) {
      log.info("Real Time");
      log.info(Thread.currentThread().getName());
      Thread.sleep(5000);
    }
  }
}
