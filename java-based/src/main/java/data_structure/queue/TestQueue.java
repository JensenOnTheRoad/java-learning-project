package data_structure.queue;

import java.util.LinkedList;
import java.util.Queue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestQueue {

  /**
   * 队列Queue实现了一个先进先出（FIFO）的数据结构：
   *
   * <p>通过add()/offer()方法将元素添加到队尾；
   *
   * <p>通过remove()/poll()从队首获取元素并删除；
   *
   * <p>通过element()/peek()从队首获取元素但不删除。
   *
   * <p>要避免把null添加到队列。
   */
  @Test
  @DisplayName("队列")
  void test_queue() {
    Queue<String> queue = new LinkedList<>();

    // 添加3个元素到队列:
    String apple = "apple";
    String pear = "pear";
    String banana = "banana";

    queue.offer(apple);
    queue.offer(pear);
    queue.offer(banana);

    // 队首永远都是apple，因为peek()不会删除它:
    Assertions.assertThat(queue.peek()).isEqualTo(apple);
    Assertions.assertThat(queue.peek()).isEqualTo(apple);

    Assertions.assertThat(queue.element()).isEqualTo(apple);
    Assertions.assertThat(queue.element()).isEqualTo(apple);

    // 从队列取出元素: 先入先出
    Assertions.assertThat(queue.poll()).isEqualTo(apple);
    Assertions.assertThat(queue.poll()).isEqualTo(pear);
    Assertions.assertThat(queue.poll()).isEqualTo(banana);

    Assertions.assertThat(queue.poll()).isNull();
  }
}
