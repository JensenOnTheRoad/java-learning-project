package basic.concurrent.juc.atomic;

import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class Demo_Atomic {
  @Test
  @DisplayName("Atomic")
  void test() {
    Hashtable hashtable = new Hashtable();
    // TODO
    AtomicBoolean atomicBoolean = new AtomicBoolean();
    AtomicInteger atomicInteger = new AtomicInteger(0);
    AtomicReference<Object> objectAtomicReference = new AtomicReference<>();
    AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[] {1, 2, 3});
  }
}
