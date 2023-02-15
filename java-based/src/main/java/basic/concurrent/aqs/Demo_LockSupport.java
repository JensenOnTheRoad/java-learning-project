package basic.concurrent.aqs;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import lombok.SneakyThrows;

public class Demo_LockSupport {

  public static void main(String[] args) {

    Lock lock = new ReentrantLock();
    lock.lock();
    Demo_LockSupport t = new Demo_LockSupport();
    testA(t);
    testB(t);
  }

  private static void testA(Demo_LockSupport t) {
    Thread threadC = new Thread(() -> t.printC());
    Thread threadB = new Thread(() -> t.printB(threadC));
    Thread threadA = new Thread(() -> t.printA(threadB));

    threadA.start();
    threadB.start();
    threadC.start();
  }

  private static void testB(Demo_LockSupport t) {
    Thread threadC_Ex = new Thread(() -> t.printC_Ex());
    Thread threadB_Ex = new Thread(() -> t.printB_Ex(threadC_Ex));
    Thread threadA_Ex = new Thread(() -> t.printA_Ex(threadB_Ex));

    threadA_Ex.start();
    threadB_Ex.start();
    threadC_Ex.start();
  }

  // region

  @SneakyThrows
  private void printA(Thread thread) {
    Thread.sleep(20L);
    System.out.print("A");
    LockSupport.unpark(thread);
  }

  @SneakyThrows
  private void printB(Thread thread) {
    Thread.sleep(20L);
    LockSupport.park();
    System.out.print("B");
    LockSupport.unpark(thread);
  }

  @SneakyThrows
  private void printC() {
    Thread.sleep(20L);
    LockSupport.park();
    System.out.print("C");
  }
  // endregion

  // region

  @SneakyThrows
  private void printA_Ex(Thread thread) {
    Thread.sleep(20L);
    System.out.print("A");
  }

  @SneakyThrows
  private void printB_Ex(Thread thread) {
    Thread.sleep(20L);
    System.out.print("B");
  }

  @SneakyThrows
  private void printC_Ex() {
    Thread.sleep(20L);
    System.out.print("C");
  }

  // endregion

}
