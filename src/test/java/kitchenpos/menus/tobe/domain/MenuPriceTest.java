package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuPriceTest {

    @Test
    @DisplayName("성공")
    void success() {
        //given
        int price = 18000;

        //when
        MenuPrice menuPrice = new MenuPrice(18000);

        //then
        assertThat(menuPrice.getValue()).isEqualTo(price);

    }

    @Test
    @DisplayName("0미만의 값을 올수 없다.")
    void canNotHaveMinus() {
        assertThatThrownBy(() -> new MenuPrice(-1))
                .isExactlyInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new MenuPrice(-1000))
                .isExactlyInstanceOf(IllegalArgumentException.class);
    }

}
