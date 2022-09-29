package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PriceTest {

  @ParameterizedTest
  @DisplayName("상품가격을 생성할 수 있다.")
  @ValueSource(longs = {0, 16_000L})
  void createPrice(long value) {
    Price price = new Price(BigDecimal.valueOf(value));
    assertThat(price).isEqualTo(new Price(BigDecimal.valueOf(value)));
  }

}