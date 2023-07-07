package basic._enum;

import java.util.EnumMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
@Slf4j
public class TestEnum {

  private EnumMap<DataBaseType, String> databases = new EnumMap<>(DataBaseType.class);

  public static void main(String[] args){

  }
  @Test
  @DisplayName("EnumMap 是专门为枚举类型量身定做的 Map 实现")
  void test_() {

    String mysqlUrl = "jdbc:mysql://localhost/db1";
    String oracleUrl = "jdbc:oracle:thin:@localhost:1521:sample";

    EnumMap<DataBaseType, String> urls = databases;
    urls.put(DataBaseType.MYSQL, mysqlUrl);
    urls.put(DataBaseType.ORACLE, oracleUrl);

    Assertions.assertThat(urls.get(DataBaseType.MYSQL)).isEqualTo(mysqlUrl);
    Assertions.assertThat(urls.get(DataBaseType.ORACLE)).isEqualTo(oracleUrl);
  }

  private enum DataBaseType {
    MYSQL("mysql", "MySQL") {
      @Override
      Object enhancer(T type) {
        return super.enhancer(type);
      }
    },

    ORACLE("oracle", "Oracle") {};

    DataBaseType(String code, String description) {}

    // a function that can be implemented by enum
    Object enhancer(T type) {
      return null;
    }
  }
}
