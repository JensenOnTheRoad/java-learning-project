package data_structure.queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen
 */
public class TestPriorityQueue {
  /** PriorityQueue实现了一个优先队列：从队首获取元素时，总是获取优先级最高的元素。 */
  @Test
  @DisplayName("PriorityQueue优先队列")
  void test() {
    Queue<User> q = new PriorityQueue<>(new UserComparator());
    // 添加3个元素到队列:
    q.offer(new User("Bob", "A1"));
    q.offer(new User("Alice", "A2"));
    q.offer(new User("Boss", "V1"));
    System.out.println(q.poll()); // Boss/V1
    System.out.println(q.poll()); // Bob/A1
    System.out.println(q.poll()); // Alice/A2
    System.out.println(q.poll()); // null,因为队列为空
  }
}

/**
 * 重写比较方法
 *
 * @author jensen_deng
 * @date 2023/05/31
 */
class UserComparator implements Comparator<User> {
  @Override
  public int compare(User u1, User u2) {
    if (u1.number.charAt(0) == u2.number.charAt(0)) {
      // 如果两人的号都是A开头或者都是V开头,比较号的大小:
      return u1.number.compareTo(u2.number);
    }
    if (u1.number.charAt(0) == 'V') {
      // u1的号码是V开头,优先级高:
      return -1;
    } else {
      return 1;
    }
  }
}

@Data
class User {
  public final String name;
  public final String number;

  @Override
  public String toString() {
    return name + "/" + number;
  }
}
