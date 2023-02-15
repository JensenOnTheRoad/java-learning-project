package spring.real_time_task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 实时任务通过ApplicationListener<ContextRefreshedEvent>实现
 *
 * @author jensen_deng
 * @date 2023/01/03
 */
@Component
@Slf4j
public class RealTimeImpl3 implements ApplicationListener<ContextRefreshedEvent> {

  @Autowired ListenerTask listenerTask;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("-event-");
    listenerTask.task();
  }
}
