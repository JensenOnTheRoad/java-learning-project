package sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestQuickSort {

  @Test
  @DisplayName("快速排序")
  void test() {
    int[] array = {71, 6, 57, 88, 60, 42, 83, 73, 48, 85, 20, 2};

    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();

    int left = 0;
    int right = array.length - 1;
    int[] b = quickSort(array, left, right);

    // 输出数组a
    for (int k = 0; k < b.length; k++) {
      System.out.print(b[k] + " ");
    }
    System.out.println();
  }

  private static int[] quickSort(int[] array, int left, int right) {
    if (left < right) {
      int i = left;
      int j = right;
      int x = array[left];

      while (i < j) {

        // 判断j位置值与基准数进行比较
        while (i < j && array[j] > x) {
          j--;
        }
        if (i < j) {
          array[i++] = array[j];
        }

        // 判断i位置值与基准数进行比较
        while (i < j && array[i] < x) {
          i++;
        }

        if (i < j) {
          array[j--] = array[i];
        }
      }

      array[i] = x;

      quickSort(array, left, j - 1); // 对左半部分进行快速排序
      quickSort(array, j + 1, right); // 对左半部分进行快速排序
    }
    return array;
  }
}
