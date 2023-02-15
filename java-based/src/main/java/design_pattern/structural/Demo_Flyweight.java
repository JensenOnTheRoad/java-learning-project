package design_pattern.structural;

import java.util.HashMap;
import java.util.Optional;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 享元（Flyweight）模式的定义：运用共享技术来有效地支持大量细粒度对象的复用。
 *
 * @author jensen_deng
 */

// 抽象享元角色
interface Flyweight {
  public void operation(UnsharedConcreteFlyweight state);
}

// 非享元角色
@Data
class UnsharedConcreteFlyweight {
  private String info;

  public UnsharedConcreteFlyweight(String info) {
    this.info = info;
  }
}

// 具体享元角色
class ConcreteFlyweight implements Flyweight {
  private String key;

  public ConcreteFlyweight(String key) {
    this.key = key;
    System.out.println("具体享元 " + key + " 被创建");
  }

  @Override
  public void operation(UnsharedConcreteFlyweight outState) {
    System.out.println("具体享元 " + key + " 被调用");
    System.out.println("非享元信息是： " + outState.getInfo());
  }
}

// 享元工厂角色
class FlyweightFactory {
  private HashMap<String, Flyweight> flyweightMap = new HashMap<String, Flyweight>();

  public Flyweight getFlyweight(String key) {
    Flyweight flyweight = flyweightMap.get(key);

    if (Optional.ofNullable(flyweight).isPresent()) {
      System.out.println("具体享元" + key + "已经存在，被成功获取！");
    } else {
      flyweight = new ConcreteFlyweight(key);
      flyweightMap.put(key, flyweight);
    }
    return flyweight;
  }
}

public class Demo_Flyweight {

  @Test
  @DisplayName("享元工程模式")
  void should_() {

    FlyweightFactory factory = new FlyweightFactory();

    Flyweight f_a1 = factory.getFlyweight("a");
    Flyweight f_a2 = factory.getFlyweight("a");
    Flyweight f_a3 = factory.getFlyweight("a");
    f_a1.operation(new UnsharedConcreteFlyweight("第1次调用"));
    f_a2.operation(new UnsharedConcreteFlyweight("第2次调用"));
    f_a3.operation(new UnsharedConcreteFlyweight("第3次调用"));

    System.out.println("-".repeat(100));

    Flyweight f_b1 = factory.getFlyweight("b");
    Flyweight f_b2 = factory.getFlyweight("b");
    f_b1.operation(new UnsharedConcreteFlyweight("第1次调用"));
    f_b2.operation(new UnsharedConcreteFlyweight("第2次调用"));
  }
}
