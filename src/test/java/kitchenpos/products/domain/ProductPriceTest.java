package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.products.domain.tobe.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductPriceTest {

  @DisplayName("상품의 가격을 생성할 수 있다.")
  @Test
  void createProductPrice() {
    ProductPrice actual = ProductPrice.from(20_000L);

    assertAll(
        () -> assertThat(actual.getPrice()).isEqualTo(BigDecimal.valueOf(20_000L))
    );
  }

  @DisplayName("상품의 가격은 0원 이상이어야 한다.")
  @ParameterizedTest
  @ValueSource(longs = {-10_000L, -1L})
  void changeProductPriceWithNegativePrice(long price) {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> ProductPrice.from(price))
        .withMessageContaining("상품의 가격은 0원 이상이어야 한다.");
  }

  @DisplayName("상품의 가격은 Null일수 없어야 한다.")
  @ParameterizedTest
  @NullSource
  void changeProductPriceWithNullPrice(Long price) {
    assertThatExceptionOfType(NullPointerException.class)
        .isThrownBy(() -> ProductPrice.from(price));
  }
}
