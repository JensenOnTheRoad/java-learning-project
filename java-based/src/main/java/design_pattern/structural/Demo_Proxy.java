package design_pattern.structural;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// 抽象主题
interface Subject {
  void request();
}

// 真实主题
class RealSubject implements Subject {

  @Override
  public void request() {
    System.out.println("访问真实主题方法");
  }
}

// 代理
class Proxy implements Subject {
  private RealSubject realSubject;

  @Override
  public void request() {
    if (realSubject == null) {
      realSubject = new RealSubject();
    }
    preRequest();
    realSubject.request();
    postRequest();
  }

  private void preRequest() {
    System.out.println("--前置处理--");
  }

  private void postRequest() {
    System.out.println("--后置处理--");
  }
}

/**
 * @author jensen_deng
 */
public class Demo_Proxy {
  @Test
  @DisplayName("代理模式")
  public void should_() {
    Proxy proxy = new Proxy();
    proxy.request();
  }
}
