package design_pattern.structural;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// 目标接口
interface Target {
  void request();
}

// 适配者
class Adapter {
  void specificRequest() {
    System.out.println("-".repeat(10) + "  适配者业务代码被调用  " + "-".repeat(10));
  }
}

// region 类适配器类

class ClassAdaptor extends Adapter implements Target {

  @Override
  public void request() {
    super.specificRequest();
  }
}
// endregion

// region 对象适配器类
class ObjectAdapter implements Target {

  private Adapter adapter;

  public ObjectAdapter(Adapter adapter) {
    this.adapter = adapter;
  }

  @Override
  public void request() {
    adapter.specificRequest();
  }
}

// endregion

public class Demo_Adapter {
  @Test
  @DisplayName("类适配器")
  public void should_() {
    Target target = new ClassAdaptor();
    target.request();
  }

  @Test
  @DisplayName("对象适配器")
  public void should_1() {
    Adapter adapter = new Adapter();
    Target target = new ObjectAdapter(adapter);
    target.request();
  }
}
