package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductPriceTest {

    @Test
    @DisplayName("상품가격을 생성한다.")
    void createProductPrice() {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(15000);

        // when
        ProductPrice price = new ProductPrice(priceValue);

        // then
        assertThat(price).isEqualTo(new ProductPrice(priceValue));
    }

    @Test
    @DisplayName("상품 가격이 0보다 작을 경우 Exception을 발생시킨다.")
    void createProductPriceIfUnderZeroThrowException() {
        // given
        BigDecimal priceValue = BigDecimal.valueOf(-1000);

        // when
        // then
        assertThatThrownBy(() -> new ProductPrice(priceValue))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
