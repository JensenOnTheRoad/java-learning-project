package spring.real_time_task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 实时任务通过ApplicationListener<ContextRefreshedEvent>实现
 *
 * @author jensen_deng
 * @date 2023/01/03
 */
//@Component
@Slf4j
public class RealTimeImpl2 implements ApplicationListener<ContextRefreshedEvent> {

  Thread thread = new Thread(new ListenerTask());

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (!thread.isAlive()) {
      thread.start();
    }
  }
}
