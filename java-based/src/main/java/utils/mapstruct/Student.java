package utils.mapstruct;

import java.time.LocalDate;
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
public class Student {

  private String name;
  private int age;
  private GenderEnum gender;
  private Double height;
  private LocalDate birthday;
}
