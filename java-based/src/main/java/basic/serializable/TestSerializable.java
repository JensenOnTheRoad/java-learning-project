package basic.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestSerializable {

  @Test
  @DisplayName("序列化与反序列化")
  void test_serializable_and_unserializable() {
    String path = "temporary/serializable.txt";

    serializable(path);
    unserializable(path);
  }

  @SneakyThrows
  static void serializable(String url) {
    String path = createNewFile(url);
    FileOutputStream fileOut = new FileOutputStream(path);
    ObjectOutputStream out = new ObjectOutputStream(fileOut);

    Person person = Person.builder().name("zhang shan").age(10).password("123456").build();
    out.writeObject(person);
    out.close();
    fileOut.close();
  }

  static void unserializable(String url) {
    try (FileInputStream in = new FileInputStream(url);
        ObjectInputStream oin = new ObjectInputStream(in)) {

      Person person = (Person) oin.readObject();
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
  private static final long serialVersionUID = 8656128222714547171L;
  private String name;
  private int age;
  private transient String password;
}
