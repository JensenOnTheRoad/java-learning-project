package link_listed;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 双向链表
 *
 * @author jensen_deng
 */
public class TestDoublyLinked {

  private Node<Integer> node;

  @BeforeEach
  void setUp() {
    node = new Node<>(0, null);
    Node<Integer> pre = node;
    for (int i = 0; i < 5; i++) {
      pre.setNext(new Node<>(i + 1, pre));
      pre = pre.getNext();
    }
  }

  @Test
  @DisplayName("双链表翻转")
  void test_doubly_linked_reverse() {
    Node.print(node);

    node = reverse(node);
    System.out.println();

    Node.print(node);
  }

  private Node<Integer> reverse(Node<Integer> node) {
    Node<Integer> tail = null;
    Node<Integer> cur = node;

    while (cur != null) {
      Node<Integer> next = cur.getNext();

      cur.setNext(tail);
      cur.setPre(next);

      tail = cur;
      cur = next;
    }
    return tail;
  }
}

@Getter
@Setter
class Node<T> {
  private T data;
  private Node<T> pre;
  private Node<T> next;

  public Node(T data, Node<T> pre) {
    this.data = data;
    this.pre = pre;
  }

  public static void print(Node<?> node) {
    while (node != null) {
      Integer data = (Integer) node.getData();
      System.out.print(data + "\t");
      node = node.getNext();
    }
  }
}
