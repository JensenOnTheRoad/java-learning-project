package utils.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestNested {

  @BeforeEach
  void setup() {
    System.out.println("before each");
  }

  @Test
  void test1() {
    System.out.println("test 1 executed");
  }

  @Nested
  class Nest {
    @Test
    void test1() {
      System.out.println("test 2-1 executed");
    }

    @Test
    void test2() {
      System.out.println("test 2-2 executed");
    }
  }
}
