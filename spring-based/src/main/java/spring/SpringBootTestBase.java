package spring;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;

/**
 * Spring单元测试的父类
 *
 * <p>关于Spring的单元测试都要继承此类，否则无法获取到Bean
 *
 * @author jensen_deng
 */
@SpringBootTest(
    classes = StartApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
// 单元测试回滚
@Transactional(rollbackFor = Exception.class)
// 指定测试文件
@ActiveProfiles("test")
public abstract class SpringBootTestBase extends Assertions {

  private static final MySQLContainer<?> MYSQL_DB;
  private static final Integer CONTAINER_MYSQL_DB_PORT = 3306;
  private static final Integer HOST_MYSQL_DB_PORT = 13306;

  /*
    创建启动容器并指定端口
  */
  static {
    MYSQL_DB =
        new MySQLContainer<>("mysql:8.0.28")
            // 设置映射端口
            .withCreateContainerCmdModifier(
                cmd ->
                    cmd.withPortBindings(
                        new PortBinding(
                            Ports.Binding.bindPort(HOST_MYSQL_DB_PORT),
                            new ExposedPort(CONTAINER_MYSQL_DB_PORT))))
            // 设置环境参数
            .withEnv(
                Map.of(
                    "MYSQL_ROOT_PASSWORD",
                    "root_password",
                    "MYSQL_DATABASE",
                    "petclinic",
                    "MYSQL_USER",
                    "petclinic",
                    "MYSQL_PASSWORD",
                    "petclinic"));
    MYSQL_DB.start();
  }

  /** 动态数据源配置 */
  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.driver-class-name", MYSQL_DB::getDriverClassName);
    registry.add("spring.datasource.url", MYSQL_DB::getJdbcUrl);
    registry.add("spring.datasource.username", MYSQL_DB::getUsername);
    registry.add("spring.datasource.password", MYSQL_DB::getPassword);
  }

  @Test
  void test() {
    Assertions.assertThat(MYSQL_DB.isRunning()).isTrue();
  }
}
