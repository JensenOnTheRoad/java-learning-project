package utils.tree_builder;

import java.io.File;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class DemoTest {

  @Test
  @DisplayName("获得当前系统信息")
  public void test() {
    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
    File file = new File(System.getProperty("user.home"));
    System.out.println("file.getAbsoluteFile() = " + file.getAbsoluteFile());

    System.out.println(
        "file.getAbsoluteFile() = " + new File(System.getProperty("user.dir")).getAbsoluteFile());
  }
}
