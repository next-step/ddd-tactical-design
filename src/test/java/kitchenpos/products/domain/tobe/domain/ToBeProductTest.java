package kitchenpos.products.domain.tobe.domain;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ToBeProductTest {

    private ToBeProduct product;
    private BigDecimal price;

    @BeforeEach
    void setUp() {
        price = BigDecimal.valueOf(30_000L);
        product = new ToBeProduct("돈가스", price, false);
    }

    @DisplayName("상품이름이 빈값이나거 null 이면 등록할 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void newProduct(String name) {
        assertThatThrownBy(() -> new ToBeProduct(name, price, false))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품이름은 비속어를 가질 수 없다.")
    @Test
    void profanityName() {
        assertThatThrownBy(() -> new ToBeProduct("김밥", price, true))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격을 변경한다")
    @Test
    void changePrice() {
        product.changePrice(BigDecimal.valueOf(15_000L));
        assertThat(product.getPrice().equals(Price.of(15_000L)))
            .isTrue();
    }

    @DisplayName("상품 가격이 입력 금액보다 크다")
    @Test
    void isGreaterThanPrice() {
        assertThat(product.isGreaterThanPrice(BigDecimal.valueOf(29_999L)))
            .isTrue();
    }
}
