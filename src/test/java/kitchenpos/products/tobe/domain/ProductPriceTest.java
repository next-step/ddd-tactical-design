package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import kitchenpos.products.tobe.ProductFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

  @DisplayName("상품 가격을 등록할 수 있다.")
  @Test
  void create() {
    BigDecimal expected = new BigDecimal(500L);
    ProductPrice productPrice = ProductFixture.createProductPrice(expected);
    assertThat(productPrice.getPrice()).isEqualTo(expected);
  }

  @DisplayName("상품의 가격은 0원 이상이어야 한다.")
  @NullSource
  @ValueSource(strings = {"-1000", "-5000"})
  @ParameterizedTest
  void create(BigDecimal expected) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> ProductFixture.createProductPrice(expected));
  }
}
