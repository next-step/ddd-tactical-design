package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuProductsTest {

    @Test
    @DisplayName("메뉴 상품의 총 가격을 계산 할 수 있다.")
    void calculateTotalPrice() {
        // given
        BigDecimal price = BigDecimal.valueOf(10000);
        MenuProducts menuProducts = MenuProducts.of(
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L),
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L)
        );

        // when
        BigDecimal totalPrice = menuProducts.calculateTotalPrice();

        // then
        assertEquals(BigDecimal.valueOf(20000), totalPrice);
    }

    @Test
    @DisplayName("메뉴 상품이 없을 경우 총 가격은 0이다.")
    void calculateTotalPriceWhenEmpty() {

        // given
        MenuProducts menuProducts = MenuProducts.of();

        // when
        BigDecimal totalPrice = menuProducts.calculateTotalPrice();

        // then
        assertEquals(BigDecimal.ZERO, totalPrice);
    }

    @Test
    @DisplayName("메뉴 상품의 식별자를 가지고 특정 메뉴 상품을 찾을 수 있다.")
    void get() {

        UUID productId = UUID.randomUUID();
        BigDecimal price = BigDecimal.valueOf(10000);

        // given
        MenuProducts menuProducts = MenuProducts.of(
                TobeMenuProduct.create(productId, price, 1L),
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L)
        );

        // when
        TobeMenuProduct menuProduct = menuProducts.get(productId).orElseThrow();

        // then
        assertEquals(productId, menuProduct.productId());
    }

    @Test
    @DisplayName("메뉴상품의 식별자가 없는 경우 빈 Optional을 반환한다.")
    void getWhenEmpty() {
        // given
        BigDecimal price = BigDecimal.valueOf(10000);
        MenuProducts menuProducts = MenuProducts.of(
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L),
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L)
        );

        // when
        var menuProduct = menuProducts.get(UUID.randomUUID());

        // then
        assertEquals(Optional.empty(), menuProduct);
    }

    @Test
    @DisplayName("메뉴의 가격이 상품 가격의 총합에 비교 할 수 있다.")
    void isOverProductPrice() {
        // given
        BigDecimal price = BigDecimal.valueOf(10000);

        MenuProducts menuProducts = MenuProducts.of(
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L),
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L)
        );
        // when
        boolean isFalse = menuProducts.isOverProductPrice(BigDecimal.valueOf(20000));
        boolean isTrue = menuProducts.isOverProductPrice(BigDecimal.valueOf(21000));

        // then
        assertFalse(isFalse);
        assertTrue(isTrue);
    }

}
