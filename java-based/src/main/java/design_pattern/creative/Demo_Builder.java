package design_pattern.creative;

// region 创建一个表示食物条目和包装的接口

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// region 产品

interface Item {
  public String name();

  public Packing packing();

  public BigDecimal price();
}


interface Packing {
  public String pack();
}

// endregion

// 创建实现Packing的实体类

class Wrapper implements Packing {

  @Override
  public String pack() {
    return "Wrapper";
  }
}

class Bottle implements Packing {

  @Override
  public String pack() {
    return "Bottle";
  }
}

// region 实现Item接口的抽象类，提供了默认功能

abstract class Burger implements Item {
  @Override
  public Packing packing() {
    return new Wrapper();
  }

  @Override
  public abstract BigDecimal price();
}

abstract class CoolDrink implements Item {
  @Override
  public Packing packing() {
    return new Wrapper();
  }

  @Override
  public abstract BigDecimal price();
}
// endregion

// region 创建拓展了Burger和ColdDrink的实体类
class VegetableBurger extends Burger {

  @Override
  public String name() {
    return "Vegetable Burger";
  }

  @Override
  public BigDecimal price() {
    return new BigDecimal(25);
  }
}

class BeefBurger extends Burger {

  @Override
  public String name() {
    return "Beef Burger";
  }

  @Override
  public BigDecimal price() {
    return new BigDecimal(50);
  }
}

class Coke extends CoolDrink {

  @Override
  public String name() {
    return "Coke";
  }

  @Override
  public BigDecimal price() {
    return new BigDecimal(15);
  }
}

class OrangeJuice extends CoolDrink {

  @Override
  public String name() {
    return "Orange Juice";
  }

  @Override
  public BigDecimal price() {
    return new BigDecimal(10);
  }
}

// endregion

// region 创建一个Set Meal带有上面定义的Item对象

class Meal {
  private List<Item> items = new ArrayList<>();

  public void addItem(Item item) {
    items.add(item);
  }

  public BigDecimal getCost() {
    AtomicReference<BigDecimal> cost = new AtomicReference<>(new BigDecimal(0));
    items.forEach(item -> cost.set(cost.get().add(item.price())));
    return cost.get();
  }

  public void showItems() {
    items.forEach(
        item -> {
          System.out.println("Item : " + item.name());
          System.out.println("Price : " + item.price());
          System.out.println("Packing : " + item.packing().pack());
          System.out.println();
        });
  }
}

// region 创建一个SetMealBuilder类，实际的Builder类负责常见Meal对象
class SetMealBuilder {

  public Meal prepareVegetableMeal() {
    Meal meal = new Meal();
    meal.addItem(new VegetableBurger());
    meal.addItem(new OrangeJuice());
    return meal;
  }

  public Meal prepareNonVegetableMeal() {
    Meal meal = new Meal();
    meal.addItem(new BeefBurger());
    meal.addItem(new Coke());
    return meal;
  }
}
// endregion

// endregion
/**
 * @author jensen_deng
 */
public class Demo_Builder {
  @Test
  @DisplayName("建造者模式")
  public void should_() {
    SetMealBuilder builder = new SetMealBuilder();
    System.out.println("-".repeat(50));

    Meal vegetableMeal = builder.prepareNonVegetableMeal();
    vegetableMeal.showItems();
    System.out.println("Total cost : " + vegetableMeal.getCost());

    System.out.println("-".repeat(50));

    Meal nonVegetableMeal = builder.prepareNonVegetableMeal();
    nonVegetableMeal.showItems();
    System.out.println("Total cost : " + nonVegetableMeal.getCost());
  }
}
