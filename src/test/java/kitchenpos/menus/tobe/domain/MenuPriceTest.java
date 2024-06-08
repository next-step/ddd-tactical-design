package kitchenpos.menus.tobe.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenuPriceTest {

    @Test
    @DisplayName("메뉴의 가격을 생성할 수 있다.")
    void create() {
        // given
        BigDecimal price = BigDecimal.valueOf(10000L);
        final MenuPrice menuPrice = MenuPrice.of(price);

        // then
        assertEquals(price, menuPrice.price());
    }

    @Test
    @DisplayName("메뉴의 가격이 0보다 작을 수 없다.")
    void validatePrice() {
        // given
        BigDecimal price = BigDecimal.valueOf(-10000L);

        // when & then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> MenuPrice.of(price))
                .withMessage("가격은 0 이상이어야 합니다.");
    }

}
