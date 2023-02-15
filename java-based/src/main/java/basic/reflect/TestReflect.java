package basic.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestReflect {
  @Test
  @DisplayName("反射")
  void test() throws Exception {

    // 获取 TargetObject 类的 Class 对象并且创建 TargetObject 类实例
    Class<?> targetClass = Class.forName("basic.reflect.TargetObject");
    TargetObject targetObject = (TargetObject) targetClass.newInstance();

    // 获取 TargetObject 类中定义的所有方法
    Method[] methods = targetClass.getDeclaredMethods();
    for (Method method : methods) {
      System.out.println(method.getName());
    }

    // 获取指定方法并调用
    Method publicMethod = targetClass.getDeclaredMethod("publicMethod", String.class);

    publicMethod.invoke(targetObject, "parameter1");

    // 获取指定参数并对参数进行修改
    Field field = targetClass.getDeclaredField("value");
    // 为了对类中的参数进行修改我们取消安全检查
    field.setAccessible(true);
    field.set(targetObject, "new Value");

    // 调用 private 方法
    Method privateMethod = targetClass.getDeclaredMethod("privateMethod");
    // 为了调用private方法我们取消安全检查
    privateMethod.setAccessible(true);
    privateMethod.invoke(targetObject);
  }
}

class TargetObject {
  private final String value;

  public TargetObject() {
    value = "object field";
  }

  public void publicMethod(String s) {
    System.out.println("\nI am a public method. parameter:" + s);
  }

  private void privateMethod() {
    System.out.println("\nI am a  private method.");
  }
}
