package base_on_spring.redis;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import lombok.Generated;

/**
 * long类型序列化设置
 *
 * @author jensen_deng
 */
@Generated
public class GsonLong implements JsonSerializer<Long>, JsonDeserializer<Long> {

  @Override
  public Long deserialize(
      JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
      throws JsonParseException {
    return Long.valueOf(jsonElement.getAsString());
  }

  @Override
  public JsonElement serialize(
      Long aLong, Type type, JsonSerializationContext jsonSerializationContext) {
    return new JsonPrimitive(String.valueOf(aLong));
  }
}
