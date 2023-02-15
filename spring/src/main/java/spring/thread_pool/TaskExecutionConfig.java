package spring.thread_pool;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author jensen_deng
 * @date 2023/01/05
 */
@Configuration
// 利用@EnableAsync注解开启异步任务支持
@EnableAsync(proxyTargetClass = true)
// @ComponentScan({"spring.thread_pool"})// 必须加此注解扫描包
public class TaskExecutionConfig {

  // 获取当前机器的核数
  public static final int CPU_NUM = Runtime.getRuntime().availableProcessors();

  // region 可配置多个线程池

  @Bean("residentTaskExecutor")
  public Executor residentTaskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    // 核心线程大小
    taskExecutor.setCorePoolSize(CPU_NUM);
    // 最大线程大小
    taskExecutor.setMaxPoolSize(CPU_NUM * 2);
    // 队列最大容量
    taskExecutor.setQueueCapacity(500);
    // 当提交的任务个数大于QueueCapacity，就需要设置该参数，但spring提供的都不太满足业务场景，可以自定义一个，也可以注意不要超过QueueCapacity即可
    taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    taskExecutor.setAwaitTerminationSeconds(60);
    taskExecutor.setThreadNamePrefix("resident_task-thread");
    taskExecutor.initialize();
    return taskExecutor;
  }

  @Bean("scheduleTaskExecutor")
  public Executor scheduleTaskExecutor() {
    ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
    // 核心线程大小
    taskExecutor.setCorePoolSize(CPU_NUM);
    // 最大线程大小
    taskExecutor.setMaxPoolSize(CPU_NUM * 2);
    // 队列最大容量
    taskExecutor.setQueueCapacity(500);
    // 当提交的任务个数大于QueueCapacity，就需要设置该参数，但spring提供的都不太满足业务场景，可以自定义一个，也可以注意不要超过QueueCapacity即可
    taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    taskExecutor.setAwaitTerminationSeconds(60);
    taskExecutor.setThreadNamePrefix("processor-thread");
    taskExecutor.initialize();
    return taskExecutor;
  }

  // endregion
}
