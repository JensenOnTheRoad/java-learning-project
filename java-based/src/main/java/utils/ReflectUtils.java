package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author jensen_deng
 */
public class ReflectUtils {

  private ReflectUtils() {}

  /**
   * 字段设置值
   *
   * @param object
   * @param clazz
   */
  public static <T> void setNumberFieldToZero(T object, Class<T> clazz) {
    if (object == null) {
      return;
    }

    Field[] fields;

    try {
      fields = clazz.getDeclaredFields();
    } catch (SecurityException e) {
      return;
    }

    for (final Field field : fields) {
      if (Modifier.isStatic(field.getModifiers())) {
        continue;
      }

      switch (getHumpFieldName(field)) {
        case "byte" -> filedSetData(field, object, (byte) 0);

        case "short" -> filedSetData(field, object, (short) 0);

        case "int", "integer" -> filedSetData(field, object, 0);

        case "bigInteger" -> filedSetData(field, object, BigInteger.ZERO);

        case "float" -> filedSetData(field, object, 0f);

        case "long" -> filedSetData(field, object, 0L);

        case "bigDecimal" -> filedSetData(field, object, BigDecimal.ZERO);

        case "double" -> filedSetData(field, object, 0d);

        default -> {}
      }
    }
  }

  /**
   * 获取驼峰命名的字段名
   *
   * @param field
   * @return
   */
  private static String getHumpFieldName(Field field) {
    if (field == null) {
      return "";
    }

    String simpleName = field.getType().getSimpleName();
    return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
  }

  /**
   * 字段设置值
   *
   * @param field 字段
   * @param o 对象
   * @param v 值
   */
  private static void filedSetData(Field field, Object o, Object v) {
    filedSetData(field, o, v, true);
  }
  /**
   * 字段设置值
   *
   * @param field 字段
   * @param o 对象
   * @param v 值
   * @param onlySetNull 只设置空值
   */
  private static void filedSetData(Field field, Object o, Object v, Boolean onlySetNull) {
    // 统一开放权限，后面设置回原始值
    final boolean isAccessible = field.isAccessible();
    field.setAccessible(true);

    try {
      if (field.get(o) != null && onlySetNull) {
        return;
      }

      field.set(o, v);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      field.setAccessible(isAccessible);
    }
  }
}
