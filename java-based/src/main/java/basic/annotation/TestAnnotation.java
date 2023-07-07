package basic.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Target(ElementType.FIELD) //  注解用于字段上
@Retention(RetentionPolicy.RUNTIME) // 保留到运行时，可通过注解获取
@interface MyField {
  String description();

  int length();
}

@Target(ElementType.TYPE) //  注解用于字段上
@Retention(RetentionPolicy.RUNTIME) // 保留到运行时，可通过注解获取
@interface MyClass {
  String description();
}

@Slf4j
@MyClass(description = "15")
public class TestAnnotation {

  // 使用我们的自定义注解
  @MyField(description = "用户名", length = 12)
  private String username;

  @Test
  @DisplayName("字段注解,通过反射读取")
  public void testMyField() {

    // 获取类模板
    Class<?> clazz = TestAnnotation.class;

    // 获取所有字段
    for (Field f : clazz.getDeclaredFields()) {
      // 判断这个字段是否有MyField注解
      if (f.isAnnotationPresent(MyField.class)) {
        MyField annotation = f.getAnnotation(MyField.class);
        log.info(
            "字段:[{}], 描述:[{}], 长度:[{}]",
            f.getName(),
            annotation.description(),
            annotation.length());
      }
    }
  }

  @Test
  @DisplayName("类注解")
  void name() {
    Class<?> clazz = TestAnnotation.class;
    MyClass annotation = clazz.getAnnotation(MyClass.class);
    log.info("annotation.description() = {}", annotation.description());
  }
}
