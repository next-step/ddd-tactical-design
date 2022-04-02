package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {


    @DisplayName("상품의 가격이 정상적으로 등록된다.")
    @Test
    void productPriceSuccess() {
        // price
        final BigDecimal price = BigDecimal.valueOf(18_000);

        // when
        final ProductPrice productPrice = new ProductPrice(price);

        // then
        assertThat(productPrice.value()).isEqualTo(price);
    }


    @DisplayName("상품의 가격은 비어있거나 음수로 등록될 수 없다.")
    @NullSource
    @ValueSource(strings = {"-100", "-1000"})
    @ParameterizedTest
    void productPriceBlankOrNegative(BigDecimal price) {

        // when - then
        assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

}