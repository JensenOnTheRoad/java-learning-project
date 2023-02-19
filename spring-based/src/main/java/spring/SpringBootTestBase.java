package spring;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports.Binding;
import com.google.gson.Gson;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * Spring单元测试的父类
 *
 * <p>关于Spring的单元测试都要继承此类，否则无法获取到Bean
 *
 * @author jensen_deng
 */
@Slf4j
@SpringBootTest(
    classes = StartApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
// 单元测试回滚
@Transactional(rollbackFor = Exception.class)
// 指定测试文件
@ActiveProfiles("test")
public abstract class SpringBootTestBase extends Assertions {

  protected static final Gson gson = new Gson();

  // MySQL
  private static final MySQLContainer<?> MYSQL_DB;
  private static final Integer CONTAINER_MYSQL_DB_PORT = 3306;
  private static final Integer HOST_MYSQL_DB_PORT = 13306;

  // Redis
  private static final GenericContainer<?> REDIS;
  private static final int CONTAINER_REDIS_PORT = 6379;
  private static final int HOST_REDIS_PORT = 16379;

  // 创建启动容器并指定端口
  static {
    MYSQL_DB =
        new MySQLContainer<>(DockerImageName.parse("mysql:8.0.28"))
            .withReuse(true) // 是否开启容器复用
            // 设置映射端口
            .withCreateContainerCmdModifier(
                cmd -> {
                  Binding hostPort = Binding.bindPort(HOST_MYSQL_DB_PORT);
                  ExposedPort exposedPort = new ExposedPort(CONTAINER_MYSQL_DB_PORT);

                  PortBinding binding = new PortBinding(hostPort, exposedPort);
                  //                  cmd.withPortBindings(binding);
                  HostConfig hostConfig = cmd.getHostConfig();
                  assert hostConfig != null;
                  hostConfig.withPortBindings(binding);
                })
            // 设置环境参数
            .withEnv(
                Map.of(
                    "MYSQL_ROOT_PASSWORD",
                    "root_password",
                    "MYSQL_DATABASE",
                    "test_db",
                    "MYSQL_USER",
                    "test_user",
                    "MYSQL_PASSWORD",
                    "password"));
    MYSQL_DB.start();

    REDIS =
        new GenericContainer<>(DockerImageName.parse("redis:5.0.5"))
            .withReuse(true)
            .withCreateContainerCmdModifier(
                cmd ->
                // 對主機端口和docker中的端口進行綁定，前面的是主機端口，後面的是docker中的端口
                {
                  Binding hostPort = Binding.bindPort(HOST_REDIS_PORT);
                  ExposedPort exposedPort = new ExposedPort(CONTAINER_REDIS_PORT);

                  PortBinding binding = new PortBinding(hostPort, exposedPort);
                  cmd.withPortBindings(binding);
                });
    REDIS.start();
  }

  /** 动态数据源配置 */
  @DynamicPropertySource
  static void registerProperties(DynamicPropertyRegistry registry) {
    // MySQL
    registry.add("spring.datasource.driver-class-name", MYSQL_DB::getDriverClassName);
    registry.add("spring.datasource.url", MYSQL_DB::getJdbcUrl);
    registry.add("spring.datasource.username", MYSQL_DB::getUsername);
    registry.add("spring.datasource.password", MYSQL_DB::getPassword);

    // Redis
    registry.add("spring.redis.host", REDIS::getHost);
    registry.add("spring.redis.port", () -> REDIS.getMappedPort(CONTAINER_REDIS_PORT));
  }

  @Test
  void test() {
    Assertions.assertThat(MYSQL_DB.isRunning()).isTrue();
    Assertions.assertThat(REDIS.isRunning()).isTrue();
  }

  @AfterAll
  static void destroy() {
    MYSQL_DB.close();
    REDIS.close();
  }
}
