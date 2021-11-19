package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.exception.WrongPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.products.tobe.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_NEGATIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {
    @DisplayName("가격을 생성할 수 있다.")
    @Test
    void create() {
        //given
        BigDecimal priceInfo = BigDecimal.valueOf(16_000L);

        //when
        Price price = new Price(priceInfo);

        //then
        assertAll(
                () -> assertThat(price).isNotNull(),
                () -> assertThat(price.getPrice()).isEqualTo(priceInfo)
        );
    }

    @DisplayName("가격은 음수일 수 없다.")
    @Test
    void create_fail_negative_price() {
        //given
        BigDecimal priceInfo = BigDecimal.valueOf(-16_000L);

        //when & then
        assertThatExceptionOfType(WrongPriceException.class)
                .isThrownBy(() -> new Price(priceInfo))
                .withMessage(PRICE_SHOULD_NOT_BE_NEGATIVE);
    }

    @Test
    void 동등성() {
        Price price1 = new Price(BigDecimal.valueOf(16_000L));
        Price price2 = new Price(BigDecimal.valueOf(16_000L));
        assertThat(price1).isEqualTo(price2);
    }
}
