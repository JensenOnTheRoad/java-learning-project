package spring.persistense.jpa.test;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.SpringBootTestBase;
import spring.persistense.jpa.service.JpaUserDO;
import spring.persistense.jpa.service.JpaUserServiceImpl;

public class TestJPA extends SpringBootTestBase {
  @Autowired private JpaUserServiceImpl userService;

  @BeforeEach
  void setup() {
    userService.truncateTable();
    userService.save(JpaUserDO.builder().id(1L).name("user1").build());
  }

  @Test
  @DisplayName("查询所有用户")
  void test1() {
    Assertions.assertThat(userService.findAll()).hasSize(1);
  }

  @Test
  @DisplayName("用户查询测试")
  void test2() {
    Optional<JpaUserDO> result = userService.findOne(JpaUserDO.builder().name("user1").build());
    Assertions.assertThat(result.isPresent()).isTrue();
  }
}
