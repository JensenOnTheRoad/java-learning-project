package map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 数组去重
 *
 * @author jensen_deng
 */
public class TestMapReduceComplex {

  private List<Integer> list = List.of(1, 2, 3, 4, 5, 2, 1);

  @BeforeEach
  void setUp() {
    list = List.of(1, 2, 3, 4, 5, 2, 1);
  }

  @Test
  @DisplayName("时间复杂度O2")
  void test_() {
    list.forEach(x -> System.out.print(x + "\t"));

    List<Integer> result = duplicateElimination(list);

    System.out.println();
    result.stream().sorted().forEach(x -> System.out.print(x + "\t"));
  }

  @Test
  @DisplayName("时间复杂度O1 使用map降低时间复杂度 ")
  void test_use_map() {
    list.forEach(x -> System.out.print(x + "\t"));
    System.out.println();

    List<Integer> result = duplicateEliminationUseMap(list);

    result.forEach(x -> System.out.print(x + "\t"));
  }

  public List<Integer> duplicateElimination(List<Integer> array) {
    List<Integer> result = new ArrayList<>();

    for (int i = 0; i < array.size(); i++) {
      for (int j = i + 1; j < array.size(); j++) {
        if (array.get(i).equals(array.get(j))) {
          j = ++i;
        }
      }
      result.add(array.get(i));
    }
    return result;
  }

  private List<Integer> duplicateEliminationUseMap(List<Integer> list) {
    List<Integer> result = new ArrayList<>();
    HashMap<Integer, Integer> map = new HashMap<>();

    for (int i = 0; i < list.size(); i++) {
      if (!map.containsValue(list.get(i))) {
        Integer value = list.get(i);
        map.put(value, value);
        result.add(value);
      }
    }

    return result;
  }
}
