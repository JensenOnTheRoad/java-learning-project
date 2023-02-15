package design_pattern.creative;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class Demo_Prototype {
  @Test
  @DisplayName("通过浅克隆实现原型模式")
  public void should_() throws CloneNotSupportedException {

    RealizeType object1 = new RealizeType("Neo");
    RealizeType clone = (RealizeType) object1.clone();
    assertEquals(object1.getName(), clone.getName());
  }
}

/** 通过浅克隆实现原型模式 */
class RealizeType implements Cloneable {
  private String name;

  public RealizeType(String name) {
    this.name = name;
    System.out.println("原型创建成功！");
  }

  public String getName() {
    return name;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    System.out.println("原型复制成功！");
    return super.clone();
  }
}
