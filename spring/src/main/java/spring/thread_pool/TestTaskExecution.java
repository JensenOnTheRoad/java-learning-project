package spring.thread_pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import spring.SpringBootTestBase;

/**
 * 配置文件 {@link spring.config.TaskExecutionConfig}
 *
 * @author jensen_deng
 */
public class TestTaskExecution extends SpringBootTestBase {
  @Autowired TestTaskExecutionService testTaskExecutionService;

  @Test
  @DisplayName("使用自定义的TaskExecute")
  void test() throws ExecutionException, InterruptedException {
    Future<String> future = testTaskExecutionService.exec();
    String actual = future.get();
    Assertions.assertThat(actual).contains("resident_task-thread");
  }
}

@Slf4j
@Service
class TestTaskExecutionService {
  /**
   * 返回当前执行线程的名称
   *
   * @return {@link Future}<{@link String}>
   */
  @Async("residentTaskExecutor")
  public Future<String> exec() {
    String threadName = Thread.currentThread().getName();
    return new AsyncResult<>(threadName);
  }
}
