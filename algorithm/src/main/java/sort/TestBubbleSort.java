package sort;

import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestBubbleSort {

  @Test
  void test_bubble_sort() {
    int[] array = {72, 6, 57, 88, 60, 42, 83, 73, 48, 85, 20, 2};

    for (int i = 0; i < array.length; i++) {
      System.out.print(array[i] + " ");
    }
    System.out.println();

    int[] b = BubbleSort(array);

    // 输出数组a
    for (int k = 0; k < b.length; k++) {
      System.out.print(b[k] + " ");
    }
    System.out.println();
  }

  private int[] BubbleSort(int[] array) {
    int temp;

    for (int i = 0; i < array.length; i++) {
      for (int j = i; j < array.length; j++) {

        if (array[i] > array[j]) {
          temp = array[i];
          array[i] = array[j];
          array[j] = temp;
        }
      }
    }

    return array;
  }
}
