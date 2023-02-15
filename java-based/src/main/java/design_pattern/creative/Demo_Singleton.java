package design_pattern.creative;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class Demo_Singleton {
  @Test
  @DisplayName("饿汉单例")
  public void should_1() {
    HungrySingleton instance1 = HungrySingleton.getInstance();
    HungrySingleton instance2 = HungrySingleton.getInstance();
    assertEquals(instance1, instance2);
  }

  @Test
  @DisplayName("懒汉单例")
  public void should_2() {
    LazySingleton instance1 = LazySingleton.getInstance();
    LazySingleton instance2 = LazySingleton.getInstance();
    assertEquals(instance1, instance2);
  }
}

/**
 * 饿汉式单例在类创建的同时就已经创建好一个静态的对象供系统使用，以后不再改变，
 *
 * <p>所以是线程安全的，可以直接用于多线程而不会出现问题。
 */
class HungrySingleton {
  private static final HungrySingleton instance = new HungrySingleton();

  /** 避免被外部实例化 */
  private HungrySingleton() {}

  public static HungrySingleton getInstance() {
    return instance;
  }
}

/**
 * 类加载时没有产生单例，只有第一次使用后才会去创建这个单例
 *
 * <p>使用了volatile和synchronized实现线程安全，但是每次访问时都要同步，会影响性能，且消耗更多的资源
 */
class LazySingleton {
  private static volatile LazySingleton instance = null;

  /** 避免被外部实例化 */
  private LazySingleton() {}

  public static synchronized LazySingleton getInstance() {
    if (instance != null) {
      instance = new LazySingleton();
    }
    return instance;
  }
}
