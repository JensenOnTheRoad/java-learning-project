package spring.properties.enable_configuration_properties;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import spring.SpringBootTestBase;

/**
 * @author jensen_deng
 */
public class TestTppProperties extends SpringBootTestBase {

  @Autowired PropertiesService propertiesService;

  @Test
  @DisplayName("获取application-test.yml的配置信息")
  void test_get_yml_properties() {
    TppProperties tppProperties = propertiesService.getTppProperties();

    Assertions.assertThat(tppProperties.domain()).isEqualTo("http://gw.api.taobao.com/router/rest");
    Assertions.assertThat(tppProperties.userId()).isEqualTo(10000001);
  }
}

@Component
@EnableConfigurationProperties(TppProperties.class)
class PropertiesService {
  @Autowired TppProperties tppProperties;

  public TppProperties getTppProperties() {
    return tppProperties;
  }
}
