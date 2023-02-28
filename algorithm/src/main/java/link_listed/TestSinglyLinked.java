package link_listed;

import java.util.List;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestSinglyLinked {

  private Node node;

  @BeforeEach
  void setUp() {
    LinkedListCreator creator = new LinkedListCreator();
    node = creator.createLinkedList(List.of(1, 2, 3, 4));
    creator.printList(node);
  }

  // region 单链表翻转

  @Test
  @DisplayName("单链表翻转")
  void test_reverse() {
    Node reverse = reverse(node);
    LinkedListCreator.print(reverse);
  }

  private Node reverse(Node node) {
    Node pre = null;
    Node next = null;

    while (node != null) {
      next = node.getNext();
      node.setNext(pre);
      pre = node;
      node = next;
    }

    return pre;
  }

  // endregion

  // region 链表结构

  @Data
  class Node {
    // 链表用于存储值
    private final int value;
    // 指向下一个节点  理解为Node next更加恰当
    private Node next;

    public Node(int value) {
      this.value = value;
      this.next = null;
    }
  }

  class LinkedListCreator {
    // 构建函数
    public Node createLinkedList(List<Integer> list) {
      if (list.isEmpty()) {
        return null;
      }
      Node headNode = new Node(list.get(0));
      Node tempNode = createLinkedList(list.subList(1, list.size()));
      headNode.setNext(tempNode);
      return headNode;
    }

    // 测试方便地打印函数
    public void printList(Node node) {
      while (node != null) {
        System.out.print(node.getValue());
        System.out.print(" ");
        node = node.getNext();
      }
      System.out.println();
    }

    public static void print(Node node) {
      while (node != null) {
        System.out.print(node.getValue());
        System.out.print(" ");
        node = node.getNext();
      }
      System.out.println();
    }
  }

  // endregion
}
