package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuProductsTest {

    @DisplayName("구성품은 항상 메뉴를 가지고있어야 한다")
    @Test
    void test1() {
        //given
        UUID productUUID = UUID.randomUUID();
        long quantity = 3;

        //when && then
        assertThatThrownBy(
            () -> new MenuProduct(null, productUUID, quantity)
        ).isInstanceOf(AssertionError.class);
    }

    @DisplayName("구성품은 항상 상품ID를 가지고있어야 한다")
    @Test
    void test2() {
        //given
        Menu menu = new Menu();
        long quantity = 3;

        //when && then
        assertThatThrownBy(
            () -> new MenuProduct(menu, null, quantity)
        ).isInstanceOf(AssertionError.class);
    }
}