package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuPriceException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuPriceTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, -100, -99999})
    void 메뉴가격은_0보다_커야한다(final int negativePrice) {
        // given & when & then
        Assertions.assertThatThrownBy(() -> new MenuPrice(negativePrice))
                .isInstanceOf(InvalidMenuPriceException.class)
                .hasMessage("메뉴 가격은 0보다 커야 합니다.");
    }
}
