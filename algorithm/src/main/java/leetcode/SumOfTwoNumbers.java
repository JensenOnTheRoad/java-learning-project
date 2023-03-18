package leetcode;

import java.util.Arrays;
import java.util.HashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class SumOfTwoNumbers {

  @Test
  @DisplayName("两数之和")
  void test() {
    int[] nums = {2, 7, 11, 15};
    int target = 9;

    Arrays.stream(twoSum(nums, target)).forEach(x -> System.out.println("x = " + x));
  }

  public int[] twoSum(int[] nums, int target) {

    int[] result = new int[2];

    HashMap<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(nums[i])) {
        result[0] = i;
        result[1] = map.get(nums[i]);
        return result;
      }
      map.put(target - nums[i], i);
    }
    return result;
  }
}
