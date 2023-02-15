package design_pattern.behavioral.strategy;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 通过策略模式实现会员折扣
 *
 * <p>会员包含普通会员、超级会员、店铺会员等。
 *
 * @author Jensen Deng
 */
@Slf4j
public class Demo_Strategy_For_VIP_Discount {

  @Test
  @DisplayName("策略模式实现会员折扣")
  void should_use_strategy_pattern_for_vip_discount() {
    log.info("{}{}{}", "=".repeat(20), " normal constructor ", "=".repeat(20));

    Cashier VIP = new Cashier(new VIP());
    Cashier SVIP = new Cashier(new SVIP());
    Cashier ExVIP = new Cashier(new ExclusiveVIP());

    BigDecimal commodityPrice = BigDecimal.valueOf(100.05);
    VIP.settlement(commodityPrice);
    SVIP.settlement(commodityPrice);
    ExVIP.settlement(commodityPrice);

    log.info("{}{}{}", "=".repeat(20), " switch constructor ", "=".repeat(20));

    Cashier vip = new Cashier("vip");
    Cashier svip = new Cashier("svip");
    Cashier exclusiveVIP = new Cashier("exclusivevip");

    vip.settlement(commodityPrice);
    svip.settlement(commodityPrice);
    exclusiveVIP.settlement(commodityPrice);
  }

  interface Buyer {
    BigDecimal calculateAmount(BigDecimal orderPrice);
  }

  /**
   * 普通会员
   *
   * <p>九折优惠
   */
  static class VIP implements Buyer {
    @Override
    public BigDecimal calculateAmount(BigDecimal orderPrice) {
      return orderPrice.multiply(BigDecimal.valueOf(0.9));
    }
  }

  /**
   * 超级会员
   *
   * <p>八折优惠
   */
  static class SVIP implements Buyer {
    @Override
    public BigDecimal calculateAmount(BigDecimal orderPrice) {
      return orderPrice.multiply(BigDecimal.valueOf(0.8));
    }
  }

  /**
   * 店铺专属会员
   *
   * <p>店铺专属会员要在店铺下单金额大于28元的时候才可以享受七折优惠。
   */
  static class ExclusiveVIP implements Buyer {
    @Override
    public BigDecimal calculateAmount(BigDecimal orderPrice) {
      if (orderPrice.compareTo(BigDecimal.valueOf(28)) > 0) {
        return orderPrice.multiply(BigDecimal.valueOf(0.7));
      }
      return orderPrice;
    }
  }

  static class Cashier {
    private Buyer buyer;

    public Cashier(Buyer buyer) {
      this.buyer = buyer;
    }

    public Cashier(String type) {
      switch (type.toUpperCase()) {
        case "VIP":
          buyer = new VIP();
          break;
        case "SVIP":
          buyer = new SVIP();
          break;
        case "EXCLUSIVEVIP":
          buyer = new ExclusiveVIP();
          break;
      }
    }

    public BigDecimal settlement(BigDecimal orderPrice) {
      BigDecimal settlement = buyer.calculateAmount(orderPrice);
      log.info(settlement.toPlainString());
      return settlement;
    }
  }
}
