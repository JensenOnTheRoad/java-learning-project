package utils.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jensen_deng
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentVO {

  private String name;
  private int age;
  private String gender;
  private Double height;
  private String birthday;
}
