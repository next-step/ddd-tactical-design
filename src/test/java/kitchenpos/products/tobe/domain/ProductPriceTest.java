package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProductPriceTest {

    @DisplayName("정상적으로 상품의 가격을 생성해보자")
    @ParameterizedTest
    @ValueSource(ints = {0, 1000, 5000, 10000})
    void createProductPrice(int price) {
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(price));

        assertAll(
                () -> assertThat(productPrice).isNotNull(),
                () -> assertThat(productPrice.getPrice()).isEqualTo(BigDecimal.valueOf(price))
        );
    }

    @DisplayName("상품가격은 null이 될수 없다.")
    @Test
    void invalidProductPrice() {
        assertThatThrownBy(
                () -> new ProductPrice(null)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 가격은 음수가 될수 없다.")
    @ParameterizedTest
    @ValueSource(ints = {-100, -5000, -10000})
    void negativeProductPrice(int price) {
        assertThatThrownBy(
                () -> new ProductPrice(BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
