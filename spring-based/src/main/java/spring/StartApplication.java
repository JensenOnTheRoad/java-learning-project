package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import spring.config.ScanIgnore;

/**
 * @author jensen_deng
 */
@SpringBootApplication
@ComponentScan(
    basePackages = {"spring.*"},
    // 基于注解排除
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = ScanIgnore.class)
    })
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class);
  }
}
