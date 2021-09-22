package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PriceTest {
    @Test
    @DisplayName("가격은 0원 이상이어야 한다.")
    void invalidPrice() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Price(BigDecimal.valueOf(-1)));
    }

    @Test
    @DisplayName("생성")
    void validPrice() {
        assertDoesNotThrow(() -> new Price(BigDecimal.valueOf(100)));
    }

    @Test
    @DisplayName("동등성")
    void equalsTest() {
        Price price1 = new Price(BigDecimal.valueOf(100));
        Price price2 = new Price(BigDecimal.valueOf(100));
        assertThat(price1).isEqualTo(price2);
    }
}