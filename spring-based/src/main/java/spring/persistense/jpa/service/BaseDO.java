package spring.persistense.jpa.service;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

/**
 * @author senreysong
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@MappedSuperclass
@SuperBuilder
@FieldNameConstants
@Generated
public abstract class BaseDO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略
  @Column(name = "id") // 数据库字段名
  @Comment("id") // 字段备注
  private Long id;

  @Column(nullable = false, updatable = false)
  private Long createdBy;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdTime;

  private Long updatedBy;
  private LocalDateTime updatedTime;

  @Column(name = "deleted")
  private Boolean deleted;

  @PrePersist
  public void prePersist() {
    if (createdTime == null) {
      createdTime = LocalDateTime.now();
    }
    if (deleted == null) {
      deleted = false;
    }
    if (null == createdBy) {
      createdBy = 1L;
    }
  }

  @PreUpdate
  public void preUpdate() {
    updatedTime = LocalDateTime.now();
    updatedBy = 1L;
    if (deleted == null) {
      deleted = false;
    }
  }
}
