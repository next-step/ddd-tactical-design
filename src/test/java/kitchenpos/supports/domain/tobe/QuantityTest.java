package kitchenpos.supports.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuantityTest {

  @DisplayName("수량을 등록할 수 있다.")
  @Test
  void create() {
    Long expected = 1L;
    Quantity quantity = QuantityFixture.create(expected);
    assertThat(quantity.getQuantity()).isEqualTo(expected);
  }

  @DisplayName("수량은 0원 이상이어야 한다.")
  @ValueSource(longs = {-1000L, -5000L})
  @ParameterizedTest
  void create(Long expected) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> QuantityFixture.create(expected));
  }
}
