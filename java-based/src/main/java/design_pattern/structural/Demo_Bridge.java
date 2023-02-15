package design_pattern.structural;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 桥接模式
 *
 * <p>可以将抽象化部分与实现化部分分开，取消二者的继承关系，改用组合关系。
 *
 * <p>将抽象与实现分离，使它们可以独立变化。
 *
 * <p>它是用组合关系代替继承关系来实现，从而降低了抽象和实现这两个可变维度的耦合度。
 *
 * @author jensen_deng
 */

// 实现化角色
interface Implementor {
  public void OperationImpl();
}

// 具体实现化角色
class ConcreteImplementorA implements Implementor {

  @Override
  public void OperationImpl() {
    System.out.println("（Concrete Implementor）具体实现化角色被访问");
  }
}

// 抽象化角色
abstract class Abstraction {
  protected Implementor implementor;

  protected Abstraction(Implementor implementor) {
    this.implementor = implementor;
  }

  public abstract void Operation();
}

// 拓展抽象化角色
class RefineAbstraction extends Abstraction {

  protected RefineAbstraction(Implementor implementor) {
    super(implementor);
  }

  @Override
  public void Operation() {
    System.out.println("拓展抽象化(Refined Abstraction)角色被访问");
    implementor.OperationImpl();
  }
}

public class Demo_Bridge {
  @Test
  @DisplayName("桥接模式")
  void should_() {
    ConcreteImplementorA impl = new ConcreteImplementorA();
    Abstraction abstraction = new RefineAbstraction(impl);
    abstraction.Operation();
  }
}
