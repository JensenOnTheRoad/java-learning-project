package data_structure;

import java.util.HashMap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestHashMap {

  @Test
  @DisplayName("hashmap")
  void test() {
    HashMap<String, String> map = new HashMap<>();
    map.put("name", "zhang");
  }
}
