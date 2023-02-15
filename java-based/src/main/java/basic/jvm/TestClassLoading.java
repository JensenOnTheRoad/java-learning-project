package basic.jvm;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestClassLoading {

  @Test
  @DisplayName("寻找类加载器")
  public void should_find_class_loader() {
    ClassLoader loader = Thread.currentThread().getContextClassLoader();
    System.out.println("loader = " + loader);
    System.out.println("loader.getParent() = " + loader.getParent());
    System.out.println("loader.getParent().getParent() = " + loader.getParent().getParent());
  }
}
