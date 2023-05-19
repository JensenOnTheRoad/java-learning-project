package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class PropertyAppenderTest {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  @SneakyThrows
  public void test_() {

    User user = new User();
    user.setName("Daisy");

    log.info(user.toString());

    Map<String, Object> propertiesMap = new HashMap<>(1);
    propertiesMap.put("age", 18);

    User obj = (User) PropertyAppender.generate(user, propertiesMap);

    log.info(obj.toString());
  }
}
