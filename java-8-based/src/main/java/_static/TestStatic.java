package _static;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 外部类无法使用static关键字进行修饰
 */
public class TestStatic {

  /**
   * 静态变量只会在类加载时获取一次内存空间
   */
  static List<Integer> list = new ArrayList<>(List.of(0, 1, 2, 3, 4));

  /* 初始化静态变量，优先于main()执行。 */
  static {
    list.add(5);
  }

  @Test
  @DisplayName("利用static代码块对静态变量list进行初始化")
  void should_use_static_block_initializing_list() {
    Assertions.assertThat(list.get(5)).isEqualTo(5);
  }

  @Test
  @DisplayName("静态方法")
  void should_static_method() {
    // 静态方法无需创建对象即可调用，在类加载时就可以通过 类名.方法名 访问
    Assertions.assertThatNoException().isThrownBy(TestStatic::printElements);
  }

  static void printElements() {
    list.forEach(System.out::println);
  }

  /**
   * 静态内部类，与其外部类并无关系。两者之间是完全独立存在的。
   *
   * <p>静态成员：属于该类，数据存放在该外部类的class文件中。
   *
   * <p>普通成员：输入这个类的对象，程序运行后生成在堆栈中
   */
  static class InnerStaticClass {

    /**
     * 静态内部类可以访问外部类所有静态成员变量，包括被private修饰的私有变量。
     *
     * <p>不可以访问到外部类的普通成员变量。
     */
    static List<Integer> inner_static = list;
  }
}
