package design_pattern.behavioral.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author Jensen Deng
 */
public class Demo_Strategy {
  @Test
  @DisplayName("策略模式")
  void should_() {

    Context context = new Context(new Add());
    int res1 = context.executeStrategy(10, 5);
    System.out.println(res1);

    Context context_2 = new Context(new Multiply());
    int res2 = context_2.executeStrategy(10, 5);
    System.out.println(res2);
  }
}

interface Strategy {
  int doOperation(int x, int y);
}

class Add implements Strategy {

  @Override
  public int doOperation(int x, int y) {
    return x + y;
  }
}

class Multiply implements Strategy {

  @Override
  public int doOperation(int x, int y) {
    return x * y;
  }
}

class Context {
  private Strategy strategy;

  public Context(Strategy strategy) {
    this.strategy = strategy;
  }

  int executeStrategy(int x, int y) {
    return strategy.doOperation(x, y);
  }
}
