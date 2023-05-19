package basic.string;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestString {

  @Test
  @DisplayName("字符串正则替换")
  void test_() {
    String input = "This_is_a_test_string_for_replacement";
    String regex = "^([^_]*_[^_]*)_.*$";
    String replacement = "$1_NEW";
    String output = input.replaceAll(regex, replacement);
    System.out.println(output);
  }
}
