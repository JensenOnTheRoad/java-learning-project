package basic.spi;

import java.util.Iterator;
import java.util.ServiceLoader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestSPI {

  /**
   *
   *
   * <h1>SPI简单案例</h1>
   *
   * <p>接口在加载时，会去 <b>resource/META-INF/services</b> 下找接口的全限定名文件，再根据里面的<b>实现类全限定名称</b>加载相应的实现类。
   *
   * <p>
   *
   * <p>接口： {@link Search}
   *
   * <p>实现类：{@link FileSearch }、{@link DatabaseSearch}
   */
  @Test
  @DisplayName("SPI简单案例")
  void test() {
    ServiceLoader<Search> s = ServiceLoader.load(Search.class);

    Iterator<Search> iterator = s.iterator();

    while (iterator.hasNext()) {
      Search search = iterator.next();
      search.searchDocument("hello world");
    }
  }
}
