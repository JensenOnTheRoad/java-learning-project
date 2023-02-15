package design_pattern.creative;

import java.math.BigDecimal;
import java.util.Optional;
import org.assertj.core.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */

// region 定义接口

interface Bill {
  // 积分门槛，支付1000元以上才可获取积分
  Integer POINTS_THRESHOLD = 1000;

  void pay();

  BigDecimal amount();
default void message(BigDecimal amount) {
    String message;
    // -1小于 0等于 1大于
    int compareTo = amount.compareTo(BigDecimal.valueOf(POINTS_THRESHOLD));
    if (compareTo > 0) {
      message = String.format("%s message : 获取了%d积分 %s \n", "-".repeat(5), amount.toBigInteger(), "-".repeat(5));
    } else {
      message = String.format("%s message : 无积分 %s \n" , "-".repeat(5), "-".repeat(5));
    }
    System.out.println(message);
  }
}

// endregion

// region 实现类

record Alipay(BigDecimal amount) implements Bill {
  @Override
  public void pay() {
    System.out.println("-".repeat(5) + " 支付宝支付 " + "-".repeat(5));
  }
}
record WechatPay(BigDecimal amount) implements Bill {
  @Override
  public void pay() {
    System.out.println("-".repeat(5) + " 微信支付 " + "-".repeat(5));
  }

  @Override
  public BigDecimal amount() {
    return amount;
  }

}

// endregion

// region 工厂类

class BillFactory {
  public Bill getBill(String type, BigDecimal amount) {
    if (Strings.isNullOrEmpty(type)|| Optional.ofNullable(amount).isEmpty()) {
      return null;
    }
    if (type.equalsIgnoreCase("WechatPay")) {
      return new WechatPay(amount);
    }
    if (type.equalsIgnoreCase("AliPay")) {
      return new Alipay(amount);
    }
    return null;
  }
}

// endregion

// region 测试校验

public class Demo_Factory_Payment {
  @Test
  @DisplayName("工厂模式支付场景")
  void should_pay_the_bill() {
    System.out.println(new Alipay(new BigDecimal(0)).amount());

    BillFactory factory = new BillFactory();

    Bill aliPay = factory.getBill("AliPay", new BigDecimal(0));
    Bill aliPayWithPoint = factory.getBill("AliPay", new BigDecimal(20000));

    Bill wechatPay = factory.getBill("WechatPay", new BigDecimal(0));
    Bill wechatPayWithPoint = factory.getBill("WechatPay", new BigDecimal(10000));

    payment(aliPay);
    payment(aliPayWithPoint);

    payment(wechatPay);
    payment(wechatPayWithPoint);
  }

  private void payment(Bill bill) {
    bill.pay();
    bill.message(bill.amount());
  }

// endregion
}
