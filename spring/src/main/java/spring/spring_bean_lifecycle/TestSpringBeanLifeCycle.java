package spring.spring_bean_lifecycle;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import spring.SpringBootTestBase;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestSpringBeanLifeCycle extends SpringBootTestBase {

  // region Test

  @Autowired
  Person person;

  /**
   * {@link spring.spring_bean_lifecycle.TestSpringBeanLifeCycle.MyBeanPostProcessor}<{@link
   * String}>
   */
  @Test
  @DisplayName("经过自定义的后置处理器")
  void test_post_processor() {
    String name = person.getName();
    // 经过后处理器
    Assertions.assertThat(name).isEqualTo("zhang shan 2");
  }
  // endregion

  // region 定义Bean

  @Component
  @Data
  public static class Person
      implements DisposableBean,
      InitializingBean,
      ApplicationContextAware,
      BeanFactoryAware,
      BeanNameAware {

    private String name;

    public Person() {
      log.info("Constructor of person bean is invoked!");
    }

    // region Initializing Bean
    @Override
    public void afterPropertiesSet() {
      log.info("after Properties Set method of person bean is invoked!");
    }
    // endregion

    // region Disposable Bean

    /**
     * 销毁时获得一次回调
     */
    @Override
    public void destroy() {
      log.info("Disposable Bean Destroy method of person bean is invoked!");
    }
    // endregion

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
    }

    // region BeanFactoryAware
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      log.info("setBeanFactory method of person is invoked!");
    }

    // region BeanFactoryAware
    @Override
    public void setBeanName(String name) {
      log.info("setBeanName method of person is invoked!");
    }
    // endregion

    // region 自定义的初始化方法和销毁方法
    public void init() {
      log.info("custom init method of person bean is invoked!");
    }

    public void destroyMethod() {
      log.info("Custom Destroy method of person bean is invoked!");
    }

    // endregion
  }
  // endregion

  // region 自定义后处理器，实现BeanPostProcessor

  /**
   * 自定义后置处理器
   * <p>
   * 每个Bean初始化时都会执行
   *
   * @author jensen_deng
   */
  @Component
  public static class MyBeanPostProcessor implements BeanPostProcessor {

    /**
     * 该方法主要针对spring在bean初始化时 调用初始化方法前 进行自定义处理。
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
        throws BeansException {
      if (bean instanceof Person p) {
        log.info("post Process Before Initialization is invoked!");
        p.setName("zhang shan 1");
        return p;
      }
      return bean;
    }

    /**
     * 该方法主要针对spring在bean初始化时 调用初始化方法后 进行自定义处理。
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
        throws BeansException {
      if (bean instanceof Person p) {
        log.info("post Process after Initialization is invoked!");
        p.setName("zhang shan 2");
        return p;
      }
      return bean;
    }
  }

  // endregion
}
