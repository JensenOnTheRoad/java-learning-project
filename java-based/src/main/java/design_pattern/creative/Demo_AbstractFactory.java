package design_pattern.creative;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.assertj.core.util.Strings;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
class Demo_AbstractFactory {
  @Test
  @DisplayName("通过形状和颜色创建实现抽象工厂设计模式")
  public void should_() {
    AbstractFactory shapeFactory = new FactoryProducer().getFactory("Shape");

    Shape circle = shapeFactory.getShape("Circle");
    circle.draw();

    Shape rectangle = shapeFactory.getShape("Rectangle");
    rectangle.draw();

    assertTrue(circle instanceof Circle);
    assertTrue(rectangle instanceof Rectangle);

    AbstractFactory colorFactory = new FactoryProducer().getFactory("Color");

    Color red = colorFactory.getColor("Red");
    red.fill();

    Color blue = colorFactory.getColor("Blue");
    blue.fill();

    assertTrue(red instanceof Red);
    assertTrue(blue instanceof Blue);
  }

  interface Shape {
    void draw();
  }

  interface Color {
    void fill();
  }

  class Red implements Color {
    @Override
    public void fill() {
      System.out.println("Red.");
    }
  }

  class Blue implements Color {
    @Override
    public void fill() {
      System.out.println("Blue.");
    }
  }

  abstract class AbstractFactory {
    public abstract Color getColor(String color);

    public abstract Shape getShape(String shape);
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

  class ShapeFactory extends AbstractFactory {

    @Override
    public Color getColor(String color) {
      return null;
    }

    @Override
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

  class ColorFactory extends AbstractFactory {
    @Override
    public Color getColor(String colorType) {
      if (Strings.isNullOrEmpty(colorType)) {
        return null;
      } else if (colorType.equalsIgnoreCase("Red")) {
        return new Red();
      } else if (colorType.equalsIgnoreCase("Blue")) {
        return new Blue();
      }
      return null;
    }

    @Override
    public Shape getShape(String shape) {
      return null;
    }
  }

  class FactoryProducer {
    public AbstractFactory getFactory(String choice) {
      if (choice.equalsIgnoreCase("SHAPE")) {
        return new ShapeFactory();
      } else if (choice.equalsIgnoreCase("COLOR")) {
        return new ColorFactory();
      }
      return null;
    }
  }
}
