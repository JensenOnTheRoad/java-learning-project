package spring.config;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * GSON序列化时间格式化处理
 *
 * @author senreysong
 */
public class GsonLocalDate implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
  private static final String PATTERN = "yyyy-MM-dd";
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

  @Override
  public LocalDate deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    return LocalDate.parse(jsonElement.getAsString(), FORMATTER);
  }

  @Override
  public JsonElement serialize(
      LocalDate localDate, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(localDate.format(FORMATTER));
  }
}
