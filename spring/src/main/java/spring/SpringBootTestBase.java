package spring;

import org.assertj.core.api.Assertions;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

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
@ActiveProfiles("test")
// 单元测试回滚
//@Transactional(rollbackFor = Exception.class)
public abstract class SpringBootTestBase extends Assertions {

}
