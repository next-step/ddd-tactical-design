package kitchenpos.products.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class PriceTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        org.junit.jupiter.api.Assertions.assertDoesNotThrow(() -> new Price(BigDecimal.valueOf(10000L)));
    }

    @DisplayName("동등성 검증")
    @Test
    void equals() {
        Price price1 = new Price(BigDecimal.valueOf(10000L));
        Price price2 = new Price(BigDecimal.valueOf(10000L));
        Assertions.assertThat(price1.equals(price2))
                .isTrue();
    }

    @DisplayName("null 생성시 에러 검증")
    @Test
    void nullValue() {
        Assertions.assertThatThrownBy(() -> new Price(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("0원 미만 생성시 에러 검증")
    @Test
    void minusValue() {
        Assertions.assertThatThrownBy(() -> new Price(BigDecimal.valueOf(-1L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격 변경시 값객체로서 재할당 작동 검증")
    @Test
    void changePrice() {
        Price price = new Price(BigDecimal.valueOf(10000L));
        Price newPrice = price.changePrice(BigDecimal.valueOf(9000L));
        Assertions.assertThat(newPrice).isNotNull();
    }

}
