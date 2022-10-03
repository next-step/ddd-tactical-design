package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

  @DisplayName("상품등록")
  @Nested
  class ProductCreate {
    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
      Product product = new Product("후라이드", false, new BigDecimal(16_000L));
      assertThat(product).isNotNull();
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void create(final BigDecimal price) {
      assertThatThrownBy(() -> new Product("후라이드", false, price))
          .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(booleans = {true})
    @ParameterizedTest
    void create(boolean isProfanity) {
      assertThatThrownBy(() -> new Product("후라이드", isProfanity, new BigDecimal(16_000L)))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @DisplayName("상품가격변경")
  @Nested
  class ProductPriceChange {
    private Product product;
    @BeforeEach
    void setUp() {
      product = new Product("후라이드", false, new BigDecimal(16_000L));
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
      //given
      BigDecimal expected = new BigDecimal(15_000L);

      //when
      product.changePrice(expected);

      //then
      assertThat(product.getPrice()).isEqualTo(new ProductPrice(expected));
    }

    @DisplayName("상품의 가격이 올바르지 않으면 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    void changePrice(BigDecimal price) {
      assertThatThrownBy(() -> product.changePrice(price))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }
}