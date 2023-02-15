package design_pattern.structural;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 外观模式
 *
 * <p>一种通过为多个复杂的子系统提供一个一致的接口，而使这些子系统更加容易被访问的模式。
 *
 * @author jensen_deng
 */

// 外观角色
class Facade {
  private SubSystem01 sub1 = new SubSystem01();
  private SubSystem02 sub2 = new SubSystem02();
  private SubSystem03 sub3 = new SubSystem03();

  public void method() {
    sub1.method();
    sub2.method();
    sub3.method();
  }
}

// 子系统角色
class SubSystem01 {
  public void method() {
    System.out.println("子系统01的method()被调用！");
  }
}
// 子系统角色
class SubSystem02 {
  public void method() {
    System.out.println("子系统02的method()被调用！");
  }
}
// 子系统角色
class SubSystem03 {
  public void method() {
    System.out.println("子系统03的method()被调用！");
  }
}

public class Demo_Facade {
  @Test
  @DisplayName("外观模式")
  void should_() {
    Facade facade = new Facade();
    facade.method();
  }
}
