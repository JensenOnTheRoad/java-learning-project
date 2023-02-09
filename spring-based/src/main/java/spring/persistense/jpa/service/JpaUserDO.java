package spring.persistense.jpa.service;

import com.vladmihalcea.hibernate.type.json.JsonStringType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "jpa_user")
// 逻辑删除
@SQLDelete(sql = "update jpa_user set deleted = true where id = ?")
@Where(clause = "deleted = false")
@DynamicUpdate
// json
@TypeDef(name = "json", typeClass = JsonStringType.class)
// lombok
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants
public class JpaUserDO extends BaseDO {

  //  枚举类型
  @Enumerated(value = EnumType.STRING)
  private Sex sex;

  // json类型
  @Type(type = "json")
  @Column(columnDefinition = "json")
  private List<Long> ids;

  @Column(name = "name")
  private String name;

  @Column(name = "age")
  private Integer age;

  @Column(name = "email")
  private String email;
}

enum Sex {
  MALE("男性"),
  FEMALE("女性");

  Sex(String desc) {
    this.desc = desc;
  }

  public final String desc;
}
