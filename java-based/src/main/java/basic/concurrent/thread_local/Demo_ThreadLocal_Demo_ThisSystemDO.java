package basic.concurrent.thread_local;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserContextHolder {
  // 创建ThreadLocal保存User对象
  public static ThreadLocal<User> holder = new ThreadLocal<>();
}

class User {
  String name;

  public User(String name) {
    this.name = name;
  }
}

class Service1 {
  public void service1(User user) {
    // 给ThreadLocal赋值，后续的服务直接通过ThreadLocal获取就行了。
    UserContextHolder.holder.set(user);
    new Service2().service2();
  }
}

class Service2 {
  public void service2() {
    User user = UserContextHolder.holder.get();
    System.out.println("service2拿到的用户:" + user.name);
    new Service3().service3();
  }
}

class Service3 {
  public void service3() {
    User user = UserContextHolder.holder.get();
    System.out.println("service3拿到的用户:" + user.name);
    // 在整个流程执行完毕后，一定要执行remove
    UserContextHolder.holder.remove();
  }
}

/**
 * 用户系统
 *
 * <p>那么当一个请求进来的时候，一个线程会负责执行这个请求，
 *
 * <p>然后这个请求就会依次调用service-1()、service-2()、service-3()、service-4()，
 *
 * <p>这4个方法可能是分布在不同的类中的。
 */
public class Demo_ThreadLocal_Demo_ThisSystemDO {
  @Test
  @DisplayName("创建ThreadLocal保存User对象")
  public void should_() {
    User user = new User("jack");
    new Service1().service1(user);
  }
}
