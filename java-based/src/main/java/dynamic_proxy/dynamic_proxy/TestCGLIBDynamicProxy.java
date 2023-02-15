package dynamic_proxy.dynamic_proxy;

import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class TestCGLIBDynamicProxy {

  @Test
  @DisplayName("CGLIB动态代理")
  public void test() {
    Couple couple = new Couple();
    Cglib cglib = new Cglib(couple);
    Marry_Action marry = (Marry_Action) cglib.getProxy();
    marry.toMarry();
  }
}
/** 定义用户行为 */
interface Marry_Action {
  void toMarry();
}

/**
 * 静态代理--目标角色类
 *
 * <p>1.实现行为
 */
class Couple implements Marry_Action {
  @Override
  public void toMarry() {
    System.out.println("to Marry_Action");
  }
}

class Cglib implements MethodInterceptor {
  // 准备一个目标对象
  private final Object target;
  // 通过构造函数传入目标对象
  public Cglib(Object target) {
    this.target = target;
  }
  // 获取代理对象
  public Object getProxy() {
    // 创建Enhancer实例，也就是cglib中的一个class generator
    Enhancer enhancer = new Enhancer();
    // 通过setSuperclass方法来设置目标类
    enhancer.setSuperclass(target.getClass());
    // 通过setCallback 方法来设置拦截对象
    enhancer.setCallback(this);
    // create方法生成Target的代理类对象，并返回代理类的实例
    return enhancer.create();
  }

  /**
   * 拦截器
   *
   * <p>实现MethodInterceptor接口就必须重写intercept方法
   *
   * <p>1. 目标对象的方法调用
   *
   * <p>2. 行为增强
   *
   * @param object cglib动态生产的代理类实例
   * @param method 实体类调用的都被代理的方法引用
   * @param objects 参数列表
   * @param methodProxy 生产的代理对象对方法的代理引用
   * @return
   */
  @Override
  public Object intercept(Object object, Method method, Object[] objects, MethodProxy methodProxy)
      throws Throwable {
    // 增强行为
    before();
    Object o = methodProxy.invoke(target, objects);
    // 增强行为
    after();
    return null;
  }

  private void after() {
    System.out.println("方法执行后的增强行为");
  }

  private void before() {
    System.out.println("方法执行前的增强行为");
  }
}
