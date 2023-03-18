package spring.redis.delay_queue;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import spring.SpringBootTestBase;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestRedissonQueue extends SpringBootTestBase {

  @Autowired RedissonClient redissonClient;

  @Builder
  record Task(Long id, String name, LocalDateTime expiredTime) {}

  @Test
  @DisplayName("redisson实现延迟队列")
  void test_redisson_queue() throws InterruptedException {
    // 创建队列
    String queueKey = "test_queue";
    RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque(queueKey);
    RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);

    // 加入队列
    Task task = Task.builder().id(1L).name("test task").expiredTime(LocalDateTime.MAX).build();
    int seconds = 10;

    delayedQueue.offer(task, seconds, TimeUnit.SECONDS);
    log.info("(添加延时队列成功) 队列键：{}，队列值：{}，延迟时间：{}", queueKey, task, seconds + "秒");

    delayedQueue.offer(task, seconds, TimeUnit.SECONDS);
    log.info("(添加延时队列成功) 队列键：{}，队列值：{}，延迟时间：{}", queueKey, task, seconds + "秒");

    // 根据key获取队列
    RBlockingQueue<Object> blockingQueue = redissonClient.getBlockingQueue(queueKey);
    Task task2 = (Task) blockingQueue.take();

    Assertions.assertThat(task2).isEqualTo(task);
  }
}
