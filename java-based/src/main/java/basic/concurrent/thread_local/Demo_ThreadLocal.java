package basic.concurrent.thread_local;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Demo_ThreadLocal {

  private static ThreadLocal<String> threadLocalVar;

  @BeforeEach
  void setup() {
    threadLocalVar = new ThreadLocal<>();
  }

  @Test
  @DisplayName("ThreadLocal Test")
  void test() throws InterruptedException {

    new Thread(
            () -> {
              Demo_ThreadLocal.threadLocalVar.set("local_A");
              // 打印当前线程中本地内存中本地变量的值
              System.out.println("current : " + threadLocalVar.get());
              // 清除本地内存中的本地变量
              threadLocalVar.remove();
              // 打印本地变量
              System.out.println("after remove : " + threadLocalVar.get());
            },
            "A")
        .start();

    Thread.sleep(1000);

    new Thread(
            new Runnable() {
              public void run() {
                Demo_ThreadLocal.threadLocalVar.set("local_B");
                // 打印当前线程中本地内存中本地变量的值
                System.out.println("current : " + threadLocalVar.get());
                // 清除本地内存中的本地变量
                threadLocalVar.remove();
                System.out.println("after remove : " + threadLocalVar.get());
              }
            },
            "B")
        .start();
  }
}

// A :local_A
// after remove : null
// B :local_B
// after remove : null
