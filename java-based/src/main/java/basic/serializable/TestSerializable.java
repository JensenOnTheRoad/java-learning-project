package basic.serializable;

import java.io.*;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestSerializable {

  private static String path;

  @BeforeAll
  static void setUp() {
    path = "temporary/serializable.txt";
  }

  @Test
  @DisplayName("序列化与反序列化")
  void test_serializable() {
    serializable(path);
  }

  @Test
  @DisplayName("反序列化")
  void test_unserializable() {
    unserializable(path);
  }

  @SneakyThrows
  private static void serializable(String url) {
    String path = createNewFile(url);

    try (FileOutputStream fileOut = new FileOutputStream(path);
        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      Person person = Person.builder().name("zhang shan").age(10).password("123456").build();
      out.writeObject(person);
    }
  }

  private static void unserializable(String url) {
    try (FileInputStream in = new FileInputStream(url);
        ObjectInputStream oIn = new ObjectInputStream(in)) {

      Person person = (Person) oIn.readObject();
      log.info("反序列化：\n" + person.toString());

    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  private static String createNewFile(String path) {
    File file = new File(path);
    File createFile = new File(file.getParentFile(), file.getName());
    try {
      createFile.createNewFile();
    } catch (IOException e) {
      log.info(e.getMessage(), e);
    }
    return path;
  }
}

@Data
@Builder
class Person implements Serializable {

  @Serial private static final long serialVersionUID = 8656128222714547171L;

  private String name;

  private int age;

  // transient不会被序列化
  private transient String password;
}
