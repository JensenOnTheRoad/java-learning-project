import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 对角差分
 *
 * @link https://www.hackerrank.com/challenges/diagonal-difference/problem
 */
public class DiagonalDifference {

  @Test
  @DisplayName("对角差分")
  void test() {
    int[][] arr = {{11, 2, 4}, {4, 5, 6}, {10, 8, -12}};
    int result = function(arr);

    Assertions.assertThat(result).isEqualTo(15);
  }

  int function(int[][] arr) {
    int sum1 = 0;
    int sum2 = 0;
    for (int i = 0; i <= arr.length - 1; i++) {
      int temp = arr[i][i];
      sum1 = sum1 + temp;

      int temp2 = arr[i][arr.length - 1 - i];
      sum2 = sum2 + temp2;
    }
    return Math.abs(sum1 - sum2);
  }
}
