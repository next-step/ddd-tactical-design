package kitchenpos.eatinorders.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

  @DisplayName("수량을 생성할 수 있다.")
  @ParameterizedTest
  @ValueSource(longs = {0, 10, 100})
  void createQuantity(long value) {
    Quantity quantity = Quantity.from(value);
    assertThat(quantity).isEqualTo(Quantity.from(value));
  }

}