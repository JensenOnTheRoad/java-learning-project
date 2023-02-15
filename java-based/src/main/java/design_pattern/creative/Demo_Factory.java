package design_pattern.creative;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.base.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

interface Shape {
  void draw();
}

class Rectangle implements Shape {
  @Override
  public void draw() {
    System.out.println("Rectangle.");
  }
}

class Circle implements Shape {
  @Override
  public void draw() {
    System.out.println("Circle");
  }
}

class ShapeFactory {
  public Shape getShape(String shapeType) {
    if (Strings.isNullOrEmpty(shapeType)) {
      return null;
    } else if (shapeType.equalsIgnoreCase("Circle")) {
      return new Circle();
    } else if (shapeType.equalsIgnoreCase("Rectangle")) {
      return new Rectangle();
    }
    return null;
  }
}
/**
 * @author jensen_deng
 *
 * 支付-多个实现类，wechat/ali/...
 *
 */
public class Demo_Factory {
  @Test
  @DisplayName("通过形状创建实现工厂设计模式")
  public void should_() {
    ShapeFactory shapeFactory = new ShapeFactory();

    Shape circle = shapeFactory.getShape("Circle");
    Shape rectangle = shapeFactory.getShape("Rectangle");
    circle.draw();
    rectangle.draw();

    assertTrue(circle instanceof Circle);
    assertTrue(rectangle instanceof Rectangle);
  }
}
