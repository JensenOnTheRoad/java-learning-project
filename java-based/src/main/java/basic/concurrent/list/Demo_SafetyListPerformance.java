package basic.concurrent.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 线程安全的三种List性能比较
 *
 * <p>Vector 和 SynchronizedList 两者对比读写性能差距不大
 *
 * <p>copyOnWriteArrayList 读性能相对于其他两者读操作性能有极大提升，但写操作性能较差
 *
 * <p>copyOnWriteArrayList 顾名思义每进行一次写操作都会复制一次数组，这是非常耗时的操作。
 *
 * <p>读多写少建议使用copyOnWriteArrayList
 *
 * <p>写多读少建议使用SynchronizedList
 */
public class Demo_SafetyListPerformance {

  public static final int COUNT = 1000;

  @Test
  @DisplayName("三种线程安全列表 读性能测试")
  void test() {
    System.out.printf("%s%s%s\n", "=".repeat(20), " read ", "=".repeat(20));
    readVector();
    readSynchronizedList();
    readCopyOnWriteArrayList();
  }

  @Test
  @DisplayName("三种线程安全列表 写性能测试")
  void test_write() {
    System.out.printf("%s%s%s\n", "=".repeat(20), " write ", "=".repeat(20));
    writeVector();
    writeSynchronizedList();
    writeCopyOnWriteArrayList();
  }

  // region 读操作
  void readVector() {
    Vector<Integer> vector = new Vector<>();
    vector.add(0);
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < COUNT; i++) {
      vector.get(0);
    }
    long time2 = System.currentTimeMillis();
    System.out.println("vector: " + (time2 - time1) + " ms");
  }

  void readSynchronizedList() {
    List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
    list.add(0);
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < COUNT; i++) {
      list.get(0);
    }
    long time2 = System.currentTimeMillis();
    System.out.println("synchronizedList: " + (time2 - time1) + " ms");
  }

  void readCopyOnWriteArrayList() {
    CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
    list.add(0);
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < COUNT; i++) {
      list.get(0);
    }
    long time2 = System.currentTimeMillis();
    System.out.println("copyOnWriteArrayList: " + (time2 - time1) + " ms");
  }

  // endregion

  // region 写操作

  void writeVector() {
    Vector vector = new Vector();
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < COUNT; i++) {
      vector.add(i);
    }
    long time2 = System.currentTimeMillis();
    System.out.println("vector: " + (time2 - time1) + " ms");
  }

  void writeSynchronizedList() {
    List<Integer> list = Collections.synchronizedList(new ArrayList<>());
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < COUNT; i++) {
      list.add(i);
    }
    long time2 = System.currentTimeMillis();
    System.out.println("synchronizedList: " + (time2 - time1) + " ms");
  }

  void writeCopyOnWriteArrayList() {
    CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();
    long time1 = System.currentTimeMillis();
    for (int i = 0; i < COUNT; i++) {
      list.add(i);
    }
    long time2 = System.currentTimeMillis();
    System.out.println("copyOnWriteArrayList: " + (time2 - time1) + " ms");
  }

  // endregion
}
