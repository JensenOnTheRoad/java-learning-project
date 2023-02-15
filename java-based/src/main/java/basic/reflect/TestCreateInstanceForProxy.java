package basic.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestCreateInstanceForProxy {
  @Test
  @DisplayName("通过反射创建代理对象")
  void test() {
    MyClass myObject = new MyClass();
    MyInvocationHandler handler = new MyInvocationHandler(myObject);

    ClassLoader classLoader = MyClass.class.getClassLoader();
    Class[] interfaces = {MyInterface.class};
    MyInterface proxy = (MyInterface) Proxy.newProxyInstance(classLoader, interfaces, handler);

    proxy.method1();
  }
}

// region 1. 创建代理接口

interface MyInterface {
  void method1();
}

// endregion

// region 2.定义类

class MyClass implements MyInterface {
  public void method1() {
    System.out.println("\nMyClass.method1() called.");
  }
}
// 3. 定义增强类 --实现InvocationHandler
class MyInvocationHandler implements InvocationHandler {
  private Object target;

  public MyInvocationHandler(Object target) {
    this.target = target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // 前置增强方法
    System.out.println("\nBefore calling enhance method: " + method.getName());

    Object result = method.invoke(target, args);
    // 后置增强方法
    System.out.println("\nAfter calling enhance method: " + method.getName());

    return result;
  }
}
