package spring.bloom_filter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import spring.SpringBootTestBase;

public class TestRedissonBloomFilter extends SpringBootTestBase {
  @Autowired RedissonClient redisson;

  @Test
  @DisplayName("布隆过滤器")
  void test_bloom_file() {
    RBloomFilter<String> bloomFilter = redisson.getBloomFilter("phoneList");

    // 初始化布隆过滤器：预计元素为100000000L,误差率为3%
    bloomFilter.tryInit(100000000L, 0.03);

    // 将号码10086插入到布隆过滤器中
    bloomFilter.add("10086");

    // 判断下面号码是否在布隆过滤器中
    // 输出false
    Assertions.assertThat(bloomFilter.contains("123456")).isFalse();
    // 输出true
    Assertions.assertThat(bloomFilter.contains("10086")).isTrue();
  }
}
