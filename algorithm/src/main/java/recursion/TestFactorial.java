package recursion;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestFactorial {

  @Test
  @DisplayName("斐波那契函数")
  void test_factorial() {

    int number = 4;

    int result = factorial(number);

    System.out.println(number + " 的阶乘= " + result);
  }

  /** 递归阶乘 */
  int factorial(int n) {
    if (n != 0) // 终止条件
    {
      return n * factorial(n - 1); // 递归调用
    } else {
      return 1;
    }
  }
}
