package design_pattern.structural;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */

// 抽象构建

interface Component {

  void add(Component component);

  void remove(Component component);

  Component getChildren(Integer i);

  void operation();
}

class Leaf implements Component {
  private String name;

  public Leaf(String name) {
    this.name = name;
  }

  @Override
  public void add(Component component) {}

  @Override
  public void remove(Component component) {}

  @Override
  public Component getChildren(Integer i) {
    return null;
  }

  @Override
  public void operation() {
    System.out.println("树叶" + name + "：被访问！");
  }
}

// 树枝构件
class Composite implements Component {

  private ArrayList<Component> children = new ArrayList<>();

  @Override
  public void add(Component component) {
    children.add(component);
  }

  @Override
  public void remove(Component component) {
    children.remove(component);
  }

  @Override
  public Component getChildren(Integer i) {
    return children.get(i);
  }

  @Override
  public void operation() {
    for (Object obj : children) {
      ((Component) obj).operation();
    }
  }
}

public class Demo_Composite {
  @Test
  @DisplayName("组合设计模式")
  void should_() {

    Composite composite1 = new Composite();
    Composite composite2 = new Composite();

    Leaf leaf1 = new Leaf("1");
    Leaf leaf2 = new Leaf("2");
    Leaf leaf3 = new Leaf("3");

    composite1.add(leaf1);
    composite1.add(composite2);

    composite2.add(leaf2);
    composite2.add(leaf3);

    composite1.operation();
  }
}
