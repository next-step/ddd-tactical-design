package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

  @ParameterizedTest
  @DisplayName("가격을 생성할 수 있다.")
  @ValueSource(longs = {0, 19_000L})
  void createPrice(long value) {
    Price price = Price.from(value);
    assertThat(price).isEqualTo(Price.from(value));
  }

  @Test
  @DisplayName("가격은 0원 이상이어야 한다.")
  void createPrice_IllegalArgumentException() {
    assertThatIllegalArgumentException()
        .isThrownBy(() -> Price.from(-1));
  }
  
}