package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TobeMenuProductTest {

    @Test
    @DisplayName("메뉴 상품을 생성할 수 있다.")
    void create() {
        // given
        final UUID productId = UUID.randomUUID();
        final BigDecimal price = BigDecimal.valueOf(10000L);
        final TobeMenuProduct tobeMenuProduct = TobeMenuProduct.create(productId, price, 2L);

        // then
        assertEquals(productId, tobeMenuProduct.productId());
        assertEquals(price, tobeMenuProduct.price());
        assertEquals(2L, tobeMenuProduct.quantity());
    }

    @Test
    @DisplayName("메뉴 상품의 총합 가격을 확인할 수 있다.")
    void changePrice() {
        // given
        final UUID productId = UUID.randomUUID();
        final BigDecimal price = BigDecimal.valueOf(10000L);
        final TobeMenuProduct menuProduct = TobeMenuProduct.create(productId, price, 2L);

        // when
        BigDecimal amount = menuProduct.calculatedPrice();

        // then
        assertEquals(BigDecimal.valueOf(20000L), amount);
    }

}
