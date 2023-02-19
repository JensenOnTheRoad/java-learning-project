package spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author jensen_deng
 */
@SpringBootApplication
@ComponentScan(basePackages = {"spring.*"})
public class StartApplication {
  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class);
  }
}
