package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TobeProductPriceTest {

    @DisplayName("상품 가격 생성")
    @Test
    void createProductPrice() {
        ProductPrice productPrice = new ProductPrice(BigDecimal.valueOf(1000L));

        assertThat(productPrice).isNotNull();
    }

    @DisplayName("잘못된 금액")
    @ValueSource(longs = {-1L, -100L})
    @ParameterizedTest
    void negativePrice(final Long price) {
        assertThatThrownBy(
                () -> new ProductPrice(BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("동등성")
    @Test
    void equalPrice() {
        ProductPrice productPrice1 = new ProductPrice(BigDecimal.valueOf(1000L));
        ProductPrice productPrice2 = new ProductPrice(BigDecimal.valueOf(1000L));

        assertThat(productPrice1).isEqualTo(productPrice2);
    }
}
