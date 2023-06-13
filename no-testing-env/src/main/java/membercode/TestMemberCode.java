package membercode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author jensen_deng
 */
public class TestMemberCode {
  @Test
  void test_() {
    MemberValueCard valueCard =
        MemberValueCard.builder()
            .code(1L)
            .password("123456")
            .expiredTime(LocalDateTime.MAX)
            .value(new BigDecimal("100.00"))
            .build();

    Assertions.assertThat(valueCard.verify("123456")).isTrue();

    valueCard.consume(BigDecimal.TEN);
    Assertions.assertThat(valueCard.getValue()).isEqualTo(new BigDecimal("90.00"));
  }
}

@Data
@SuperBuilder
abstract class MemberCode {
  private Long code;
  private String password;
  private LocalDateTime expiredTime;

  public Boolean verify(String password) {
    return this.password.equals(password);
  }

  public Boolean checkExpired() {
    if (expiredTime == null) {
      throw new IllegalStateException("This code is not activated!");
    }
    return LocalDateTime.now().isAfter(expiredTime);
  }

  public abstract void consume(Number value);
}

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
class MemberValueCard extends MemberCode {
  private BigDecimal value;

  @Override
  public void consume(Number value) {
    BigDecimal cardValue = (BigDecimal) value;
    BigDecimal result = this.value.subtract(cardValue);
    if (result.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("balance is not enough.");
    } else {
      this.setValue(result);
    }
  }
}

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
class MemberCountCard extends MemberCode {
  private Integer count;

  @Override
  public void consume(Number count) {
    Integer cardCount = (Integer) count;
    if (this.count - cardCount < 0) {
      throw new IllegalArgumentException("count is not enough.");
    }
  }
}
