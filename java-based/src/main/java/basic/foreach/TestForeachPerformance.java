package basic.foreach;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
// @ExtendWith(ConcurrentTestRunner.class)
public class TestForeachPerformance {

  private static Integer NUM = 1000;

  @Test
  @DisplayName("Normal for")
  public void should_1() throws InterruptedException {
    long start = System.currentTimeMillis();
    for (int i = 0; i < NUM; i++) {
      Thread.sleep(1);
    }
    log.info("Normal For Total time spent : {}ms", System.currentTimeMillis() - start);
  }

  @Test
  @DisplayName("Steam for")
  public void should_2() {
    long start = System.currentTimeMillis();
    IntStream.rangeClosed(0, NUM)
        .forEach(
            (item) -> {
              try {
                Thread.sleep(1);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            });
    log.info("IntStream foreach共计用时{}ms", System.currentTimeMillis() - start);
  }

  @Test
  @DisplayName("parallel steam for")
  public void should_3() {
    long start = System.currentTimeMillis();
    IntStream.rangeClosed(0, NUM)
        .parallel()
        .forEach(
            (item) -> {
              try {
                Thread.sleep(1);
              } catch (InterruptedException e) {
                throw new RuntimeException(e);
              }
            });
    log.info(
        "Parallel IntStream foreach  total time spent : {}ms", System.currentTimeMillis() - start);
  }
}
