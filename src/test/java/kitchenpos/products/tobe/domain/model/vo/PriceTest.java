package kitchenpos.products.tobe.domain.model.vo;

import kitchenpos.products.tobe.domain.model.vo.Price;
import kitchenpos.products.tobe.exception.IllegalPriceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class PriceTest {

    private static final BigDecimal NORMAL_PRICE = BigDecimal.valueOf(1_000L);
    private static final BigDecimal NEGATIVE_PRICE = BigDecimal.valueOf(-1_000L);

    @Test
    @DisplayName("가격은 0보다 큰 값을 가져야 한다.")
    void create() {
        //when & then
        assertThatThrownBy(() -> new Price(NEGATIVE_PRICE))
                .isInstanceOf(IllegalPriceException.class);
    }


    @Test
    @DisplayName("객체의 값이 동일한지 비교한다.")
    void isSame() {
        //given
        Price origin = new Price(NORMAL_PRICE);
        assertThat(origin.isSame(NORMAL_PRICE)).isTrue();
    }





}
