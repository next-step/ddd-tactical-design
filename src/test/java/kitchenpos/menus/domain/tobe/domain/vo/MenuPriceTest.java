package kitchenpos.menus.domain.tobe.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class MenuPriceTest {

    @DisplayName(value = "메뉴가격을 비교한다")
    @Test
    void 메뉴가격을_비교한다() throws Exception {
        //given
        MenuPrice 가격 = new MenuPrice(BigDecimal.valueOf(18_000));

        //when
        boolean shouldBeTrue = 가격.isGreaterThan(BigDecimal.valueOf(17_000));

        //then
        assertThat(shouldBeTrue).isTrue();
    }

    @DisplayName(value = "메뉴가격은 0원 이상이다")
    @ValueSource(strings = {"0","-1"})
    @ParameterizedTest
    void 메뉴가격은_0원_이상이다(final BigDecimal price) throws Exception {
        //given
        assertThatThrownBy(() -> new MenuPrice(price))
                .isInstanceOf(IllegalArgumentException.class);

    }

}
