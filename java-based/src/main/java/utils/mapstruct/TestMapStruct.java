package utils.mapstruct;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestMapStruct {

  @Test
  @DisplayName("")
  void test() {
    Student student =
        Student.builder()
            .name("小明")
            .age(6)
            .gender(GenderEnum.Male)
            .height(121.1)
            .birthday(LocalDate.now())
            .build();
    System.out.println(student);
    // 这行代码便是实际要用的代码
    StudentVO studentVO = StudentMapper.INSTANCE.student2StudentVO(student);
    System.out.println(studentVO);
  }
}
