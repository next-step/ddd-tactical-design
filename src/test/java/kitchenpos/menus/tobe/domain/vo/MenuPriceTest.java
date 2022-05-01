package kitchenpos.menus.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class MenuPriceTest {

    @Test
    void 메뉴의_가격은_0원_이상이어야_한다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new MenuPrice(BigDecimal.valueOf(-1))).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴의_가격이_비어있을수_없다() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new MenuPrice(null)).isInstanceOf(IllegalArgumentException.class);
    }

}
