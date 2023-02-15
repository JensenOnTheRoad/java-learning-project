package design_pattern.structural;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 装饰器模式
 *
 * <p>指在不改变现有对象结构的情况下，动态地给该对象增加一些职责（即增加其额外功能）的模式
 *
 * @author jensen_deng
 */

// 抽象构建角色
interface Components {
  void operation();
}

// 具体构建角色
class ConcreteComponent implements Components {

  public ConcreteComponent() {
    System.out.println("创建具体构建角色");
    System.out.println();
  }

  @Override
  public void operation() {
    System.out.println("调用具体构建角色的方法");
  }
}

// 抽象装饰角色
class Decorator implements Components {
  private Components components;

  public Decorator(Components Components) {
    this.components = Components;
  }

  @Override
  public void operation() {
    components.operation();
  }
}

// 具体装饰角色
class ConcreteDecorator extends Decorator {

  public ConcreteDecorator(Components components) {
    super(components);
  }

  public void operation() {
    super.operation();
    addedFunction();
  }

  private void addedFunction() {
    System.out.println("具体构建角色增加的额外的功能");
  }
}

public class Demo_Decorator {

  @Test
  @DisplayName("装饰器模式")
  void should_() {
    Components components = new ConcreteComponent();
    components.operation();
    System.out.println("-".repeat(50));
    ConcreteDecorator concreteDecorator = new ConcreteDecorator(components);
    concreteDecorator.operation();
  }
}
