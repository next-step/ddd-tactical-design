package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("상품 가격")
class ProductPriceTest {

  @DisplayName("상품 가격 생성")
  @Test
  void createProductPrice() {
    ProductPrice price = new ProductPrice(20_000);
    assertThat(price.getValue()).isEqualTo(BigDecimal.valueOf(20_000));
  }

  @DisplayName("상품 가격이 0보다 작으면 에러")
  @ValueSource(longs = {-1L, -10_000L})
  @ParameterizedTest
  void createProductPriceNegative(long value) {
    assertThatThrownBy(() -> new ProductPrice(value)).isInstanceOf(IllegalArgumentException.class);
  }


}