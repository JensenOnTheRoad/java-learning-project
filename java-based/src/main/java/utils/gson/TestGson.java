package utils.gson;

import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestGson {
  @Test
  @DisplayName("gson测试")
  void test() {
    List<Integer> list = null;

    //    list = Arrays.asList(1, 2, 3);

    String toJson = new Gson().toJson(list);
    System.out.println(toJson);
  }
}
