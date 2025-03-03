package kitchenpos.tobe.product.domain;

import kitchenpos.tobe.product.domain.exception.InvalidProductPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductPriceTest {

    @Test
    @DisplayName("상품 가격을 생성할 수 있다.")
    void create() {
        // given
        final BigDecimal priceBigDecimal = BigDecimal.valueOf(16_000);
        final long priceLong = 16_000L;
        final int priceInt = 16_000;
        final double priceDouble = 16_000.0;
        final String priceString = "16000";

        // when
        final ProductPrice productPriceBigDecimal = ProductPrice.of(priceBigDecimal);
        final ProductPrice productPriceLong = ProductPrice.of(priceLong);
        final ProductPrice productPriceInt = ProductPrice.of(priceInt);
        final ProductPrice productPriceDouble = ProductPrice.of(priceDouble);
        final ProductPrice productPriceString = ProductPrice.of(priceString);

        // then
        final ProductPrice productPrice = new ProductPrice(new BigDecimal(16_000));
        assertAll(
                () -> assertThat(productPriceBigDecimal).isEqualTo(productPrice),
                () -> assertThat(productPriceLong).isEqualTo(productPrice),
                () -> assertThat(productPriceInt).isEqualTo(productPrice),
                () -> assertThat(productPriceDouble).isEqualTo(productPrice),
                () -> assertThat(productPriceString).isEqualTo(productPrice)
        );
    }

    @Test
    @DisplayName("상품 가격이 음수면 예외가 발생한다.")
    void createWithNegativePrice() {
        // given
        final long price = -1L;

        // when & then
        assertThatException()
                .isThrownBy(() -> ProductPrice.of(price))
                .isInstanceOf(InvalidProductPriceException.class);
    }

    @Test
    @DisplayName("상품 가격이 null이면 예외가 발생한다.")
    void createWithNullPrice() {
        // given
        final BigDecimal price = null;

        // when & then
        assertThatException()
                .isThrownBy(() -> ProductPrice.of(price))
                .isInstanceOf(InvalidProductPriceException.class);
    }

}
