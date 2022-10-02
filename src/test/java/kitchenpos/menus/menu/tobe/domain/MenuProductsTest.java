package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.menus.menu.tobe.domain.vo.Price;
import kitchenpos.menus.menu.tobe.domain.vo.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록을 생성한다.")
    @Nested
    class OfTest {

        @DisplayName("성공")
        @Test
        void success() {
            final MenuProduct menuProduct = MenuProduct.create(UUID.randomUUID(), Price.valueOf(10_000L), Quantity.valueOf(1L));
            final MenuProduct menuProduct2 = MenuProduct.create(UUID.randomUUID(), Price.valueOf(1_000L), Quantity.valueOf(2L));
            final MenuProducts menuProducts = MenuProducts.of(menuProduct, menuProduct2);

            assertThat(menuProducts.values().size()).isEqualTo(2);
        }

        @DisplayName("메뉴 상품은 하나 이상 포함해야 한다.")
        @Test
        void error() {
            assertThatThrownBy(() -> MenuProducts.of(Collections.emptyList()))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("메뉴 상품 목록의 금액 총합을 구한다.")
    @Test
    void totalAmount() {
        final MenuProduct menuProduct = MenuProduct.create(UUID.randomUUID(), Price.valueOf(10_000L), Quantity.valueOf(1L));
        final MenuProduct menuProduct2 = MenuProduct.create(UUID.randomUUID(), Price.valueOf(1_000L), Quantity.valueOf(2L));
        final MenuProducts menuProducts = MenuProducts.of(menuProduct, menuProduct2);

        assertThat(menuProducts.totalAmount()).isEqualTo(Price.valueOf(12_000L));
    }

    @DisplayName("메뉴 상품의 가격을 변경한다.")
    @Test
    void changePrice() {
        final UUID menuProductId = UUID.randomUUID();
        final UUID menuProduct2Id = UUID.randomUUID();
        final MenuProduct menuProduct = MenuProduct.create(menuProductId, Price.valueOf(10_000L), Quantity.valueOf(1L));
        final MenuProduct menuProduct2 = MenuProduct.create(menuProduct2Id, Price.valueOf(1_000L), Quantity.valueOf(2L));
        final MenuProducts menuProducts = MenuProducts.of(menuProduct, menuProduct2);

        menuProducts.changePrice(menuProductId, Price.valueOf(20_000L));

        assertThat(menuProducts.totalAmount()).isEqualTo(Price.valueOf(22_000L));
    }
}
