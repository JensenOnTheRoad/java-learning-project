package utils.mapstruct;

/**
 * @author jensen_deng
 */
public enum GenderEnum {
  Male("1", "男"),
  Female("0", "女");

  private final String code;
  private final String name;

  public String getCode() {
    return this.code;
  }

  public String getName() {
    return this.name;
  }

  GenderEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }
}
