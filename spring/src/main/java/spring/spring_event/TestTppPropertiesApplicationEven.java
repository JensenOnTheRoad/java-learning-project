package spring.spring_event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spring.SpringBootTestBase;

/**
 * @author jensen_deng
 */
public class TestTppPropertiesApplicationEven extends SpringBootTestBase {

  // region 测试

  @Autowired
  OrderService orderService;

  /**
   * 场景: 订单实体类
   *
   * <p>保存一个订单后发布事件通知，
   *
   * <p>以便做一些其他操作比如锁定商品：
   */
  @Test
  @DisplayName("事件监听测试")
  public void contextLoads() {
    orderService.save();
  }
  // endregion

  // region  定义实体类-Order
  @Data
  @Builder
  private static class Order {

    private String orderNo;

    private String status;

    private String goods;

    private LocalDateTime createdTime;
  }

  // endregion

  // region 订单服务-OrderService

  @Service
  @Slf4j
  public static class OrderService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 订单保存
     */
    public void save() {

      String millis = String.valueOf(System.currentTimeMillis());
      String str = millis.substring(millis.length() - 7, millis.length() - 1);
      String orderNo = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + str;

      Order order =
          Order.builder()
              .orderNo(orderNo)
              .status("待付款")
              .createdTime(LocalDateTime.now())
              .goods("phone")
              .build();

      // 发布事件
      applicationEventPublisher.publishEvent(new OrderCreateEvent(this, order));

      log.info("\n{}{}{}", "=".repeat(80), " END ", "=".repeat(80));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
      this.applicationEventPublisher = applicationEventPublisher;
    }
  }
  // endregion

  // region 订单创建事件-OrderCreateEvent
  @Getter
  @Slf4j
  public static class OrderCreateEvent extends ApplicationEvent {

    private final Order order;

    public OrderCreateEvent(Object source, Order order) {
      super(source);
      this.order = order;
    }
  }

  // endregion

  // region 监听器-OrderCreateEventListener

  @Component
  @Slf4j
  public static class OrderCreateEventListener implements ApplicationListener<OrderCreateEvent> {

    @Override
    public void onApplicationEvent(OrderCreateEvent event) {
      log.info("\n<< 订单保存成功 >>：{}", event.order);

      log.info(
          "\n\n <<{}>> \n\t--ApplicationListener接口实现，\n\t订单号：<< {} >>,\n\t锁定商品：<< {} >>\n\n",
          this.getClass().getName(),
          event.getOrder().getOrderNo(),
          event.getOrder().getGoods());
      log.info("保存日志信息");
    }
  }
  // endregion

}
