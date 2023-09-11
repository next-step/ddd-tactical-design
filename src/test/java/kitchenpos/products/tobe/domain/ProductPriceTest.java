package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {

    @DisplayName("상품 가격 생성")
    @Test
    void createProductPrice() {
        BigDecimal price = BigDecimal.valueOf(1_000L);

        ProductPrice productPrice = new ProductPrice(price);
        assertThat(productPrice).isNotNull();
    }

    @DisplayName("상품 가격이 null 일경우 예외가 발생한다")
    @ParameterizedTest
    @NullSource
    void exceptionProductPrice(BigDecimal price) {
        assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

}