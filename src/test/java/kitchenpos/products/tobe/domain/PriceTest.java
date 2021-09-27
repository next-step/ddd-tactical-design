package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PriceTest {
    @Test
    @DisplayName("상품 가격을 생성한다.")
    void create() {
        long priceValue = 100;
        Price price = new Price(BigDecimal.valueOf(priceValue));
        assertThat(price.toString()).isEqualTo(String.valueOf(priceValue));
    }

    @Test
    @DisplayName("상품 가격이 0원 이상인지 확인한다.")
    void create_validated_more_zero() {
        long priceValue = -1;
        assertThatThrownBy(() -> new Price(BigDecimal.valueOf(priceValue)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
