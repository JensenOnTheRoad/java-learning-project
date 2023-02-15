package dynamic_proxy.dynamic_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestJDKDynamicProxy {
  /** 测试 */
  @Test
  @DisplayName("JDK动态代理")
  public void test() {
    // 得到目标对象
    Couple couple = new Couple();
    // 得到代理类
    JdkHandler jdkHandler = new JdkHandler(couple);
    // 得到代理对象
    Marry marry = (Marry) jdkHandler.getProxy();
    // 通过代理对象调用对象的方法
    marry.toMarry();
  }

  /** 定义用户行为 */
  interface Marry {
    void toMarry();
  }

  /**
   * 静态代理--目标角色类
   *
   * <p>1.实现行为
   */
  class Couple implements Marry {
    @Override
    public void toMarry() {
      System.out.println("to Marry_Action");
    }
  }

  /**
   * jdk动态代理类
   *
   * <p>每一个代理类都需要实现InvocationHandler 接口,该接口要实现invoke方法
   */
  class JdkHandler implements InvocationHandler {

    private final Object target;

    public JdkHandler(Object target) {
      this.target = target;
    }

    /**
     * 获得了代理对象，那么要为目标对象增强需求 invoke
     *
     * <p>1.调用目标对象的方法(返回Object)
     *
     * <p>2.增强目标对象的行为
     *
     * <p>参数1 proxy :调用该方法的代理实例
     *
     * <p>参数2.method :目标对象的方法
     *
     * <p>参数3.args :目标对象的方法需要的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      before(); // 用户增强行为
      Object object = method.invoke(target, args); // 调用目标对象的方法
      after(); // 用户增强行为
      return object;
    }

    private void after() {
      System.out.println("方法执行后");
    }

    private void before() {
      System.out.println("方法执行前");
    }

    public Object getProxy() {
      /*
      获取一个代理类，那就要调用Proxy类中的newProxyInstance（）方法

      Proxy类是专门完成代理的操作类，
      可以通过此类为一个或多个接口动态生成实现类，
      此类提供了newProxyInstance方法，
      这个方法就是返回给你一个代理对象
       */
      return Proxy.newProxyInstance(
          this.getClass().getClassLoader(), // 当前类的类加载器
          target.getClass().getInterfaces(), // 需要一个目标对象的接口数组
          this // 每一个代理类都需要实现InvocationHandler方法，当前类实现了该接口，直接传入当前类即可
          );
    }
  }
}
