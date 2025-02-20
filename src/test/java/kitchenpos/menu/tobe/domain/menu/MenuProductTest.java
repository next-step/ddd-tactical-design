package kitchenpos.menu.tobe.domain.menu;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MenuProductTest {
    @Test
    void price_test() {
        // given
        MenuProduct menuProduct = new MenuProduct(1L, 2, 10000L, UUID.randomUUID());

        // when
        Long price = menuProduct.totalPrice();

        // then
        assertEquals(20000L, price);
    }

}