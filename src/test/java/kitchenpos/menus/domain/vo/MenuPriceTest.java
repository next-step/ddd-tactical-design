package kitchenpos.menus.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MenuPriceTest {


    @DisplayName("[정상] 메뉴 가격이 같으면 같은 객체이다.")
    @Test
    void valueObject_equals() {
        MenuPrice menuPrice1 = new MenuPrice(new BigDecimal(10_000));
        MenuPrice menuPrice2 = new MenuPrice(new BigDecimal(10_000));

        assertThat(menuPrice1).isEqualTo(menuPrice2);
    }
}