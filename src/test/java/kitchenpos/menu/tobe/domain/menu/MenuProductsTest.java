package kitchenpos.menu.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductsTest {
    @Nested
    @DisplayName("메뉴 가격 합계 확인")
    class MenuProductPriceTest{
        @Test
        @DisplayName("가격 합계 확인")
        void total_price() {
            // given
            MenuProduct menuProduct = new MenuProduct(1L, 2, 10000L, UUID.randomUUID());
            MenuProduct menuProduct2 = new MenuProduct(2L, 2, 20000L, UUID.randomUUID());
            MenuProducts menuProducts = new of(menuProduct, menuProduct2);

            // when
            boolean result = menuProducts.isTotalPriceLessThanOrEqualTo(new MenuPrice(60000L));

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("가격 합계 확인_2")
        void total_price_2() {
            // given
            MenuProduct menuProduct = new MenuProduct(1L, 2, 10000L, UUID.randomUUID());
            MenuProduct menuProduct2 = new MenuProduct(2L, 2, 20000L, UUID.randomUUID());
            MenuProducts menuProducts = new of(menuProduct, menuProduct2);

            // when
            Long l = menuProducts.calculateTotalPrice();

            // then
            assertThat(l).isEqualTo(60000L);
        }
    }
}