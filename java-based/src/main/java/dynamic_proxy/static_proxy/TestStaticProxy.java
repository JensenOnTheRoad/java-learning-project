package dynamic_proxy.static_proxy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */

/**
 * 静态代理
 *
 * <p>这就是一个简单的静态的代理模式的实现。
 *
 * <p>代理模式中的所有角色（代理对象、目标对象、目标对象的接口）等都是在编译期就确定好的。
 *
 * <p>静态代理的用途
 *
 * <p>1.控制真实对象的访问权限：通过代理对象控制真实对象的使用权限。
 *
 * <p>2.避免创建大对象：通过使用一个代理小对象来代表一个真实的大对象，可以减少系统资源的消耗，对系统进行优化并提高运行速度。
 *
 * <p>3.增强真实对象的功能：这个比较简单，通过代理可以在调用真实对象的方法的前后增加额外功能。
 */
public class TestStaticProxy {
  @Test
  @DisplayName("静态代理")
  public void test() {
    You you = new You();
    WeddingCompany weddingCompany = new WeddingCompany(you);
    weddingCompany.toMarry();
  }
}

interface Marry {
  void toMarry();
}

class You implements Marry {
  @Override
  public void toMarry() {
    System.out.println("to Marry");
  }
}

class WeddingCompany implements Marry {

  private final Marry marry;

  public WeddingCompany(Marry marry) {
    this.marry = marry;
  }

  @Override
  public void toMarry() {
    before();
    marry.toMarry();
    after();
  }

  // 行为增强
  private void before() {
    System.out.println("The Wedding is in preparation.");
  }

  private void after() {
    System.out.println("Happy wedding.");
  }
}
