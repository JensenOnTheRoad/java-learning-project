package utils.junit5;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class TestJUnit5Annotation {

  private static final String LOGGER_TEST =
      "============================== {} ==============================";

  /**
   * @BeforeAll 类似于JUnit4的@BeforeAll,
   *
   * <p>表示使用了该注解的方法应该在当前类中所有使用了@Test、@RepeatedTest、@ParameterizedTest或者@TestFactory注解的方法之前执行，
   *
   * <p>必须为static
   */
  @BeforeAll
  public static void beforeAll() {
    log.debug(LOGGER_TEST, 1);
  }

  /**
   * @AfterAll 类似于JUnit4的@AfterClass,
   *
   * <p>表示使用了该注解的方法应该在当前类中所有使用了@Test、@RepeatedTest、@ParameterizedTest或者@TestFactory注解的方法之后执行
   *
   * <p>,必须为static
   */
  @AfterAll
  public static void afterAll() {
    log.debug(LOGGER_TEST, 4);
  }

  /**
   * @BeforeEach 类似于JUnit4的@Before,
   *
   * <p>表示使用了该注解的方法应该在当前类中每一个使用了@Test、@RepeatedTest、@ParameterizedTest或者@TestFactory注解的方法之前执行
   */
  @BeforeEach
  public void beforeEach() {
    log.debug(LOGGER_TEST, 2);
  }

  /**
   * @AfterEach 类似于JUnit4的@After,
   *
   * <p>表示使用了该注解的方法应该在当前类中每一个使用了@Test、@RepeatedTest、@ParameterizedTest或者@TestFactory注解的方法之后执行。
   */
  @AfterEach
  public void afterEach() {
    log.debug(LOGGER_TEST, 3);
  }

  @DisplayName("JUnit 注解测试")
  @Test
  public void test() {
    log.debug(LOGGER_TEST, "hello world");
  }
}
