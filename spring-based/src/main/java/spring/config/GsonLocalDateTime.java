package spring.config;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * GSON序列化时间格式化处理
 *
 * @author senreysong
 */
public class GsonLocalDateTime
    implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
  private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

  @Override
  public LocalDateTime deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    return LocalDateTime.parse(jsonElement.getAsString(), FORMATTER);
  }

  @Override
  public JsonElement serialize(
      LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(localDateTime.format(FORMATTER));
  }
}
