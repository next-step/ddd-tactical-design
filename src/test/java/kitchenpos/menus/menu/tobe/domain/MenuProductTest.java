package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.menus.menu.tobe.domain.exception.InvalidMenuProductException;
import kitchenpos.menus.menu.tobe.domain.vo.Price;
import kitchenpos.menus.menu.tobe.domain.vo.Quantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다.")
    @Nested
    class CreateTest {

        @DisplayName("성공")
        @Test
        void success() {
            final UUID productId = UUID.randomUUID();
            final MenuProduct menuProduct = MenuProduct.create(productId, Price.valueOf(15_000L), Quantity.valueOf(1L));

            assertAll(
                    () -> assertThat(menuProduct.productId()).isEqualTo(productId),
                    () -> assertThat(menuProduct.price()).isEqualTo(Price.valueOf(15_000L)),
                    () -> assertThat(menuProduct.quantity()).isEqualTo(Quantity.valueOf(1L))
            );
        }

        @DisplayName("제품 정보가 있어야 합니다.")
        @Test
        void error_1() {
            assertThatThrownBy(() -> MenuProduct.create(null, Price.valueOf(15_000L), Quantity.valueOf(1L)))
                    .isInstanceOf(InvalidMenuProductException.class)
                    .hasMessage(InvalidMenuProductException.PRODUCT_ID_MESSAGE);
        }

        @DisplayName("가격 정보가 있어야 합니다.")
        @Test
        void error_2() {
            assertThatThrownBy(() -> MenuProduct.create(UUID.randomUUID(), null, Quantity.valueOf(1L)))
                    .isInstanceOf(InvalidMenuProductException.class)
                    .hasMessage(InvalidMenuProductException.PRICE_MESSAGE);
        }

        @DisplayName("수량 정보가 있어야 합니다.")
        @Test
        void error_3() {
            assertThatThrownBy(() -> MenuProduct.create(UUID.randomUUID(), Price.valueOf(15_000L), null))
                    .isInstanceOf(InvalidMenuProductException.class)
                    .hasMessage(InvalidMenuProductException.QUANTITY_MESSAGE);
        }
    }

    @DisplayName("금액은 가격 X 수량의 값이다.")
    @Test
    void amount() {
        final UUID productId = UUID.randomUUID();
        final MenuProduct menuProduct = MenuProduct.create(productId, Price.valueOf(15_000L), Quantity.valueOf(3L));

        assertThat(menuProduct.amount()).isEqualTo(Price.valueOf(45_000L));
    }
}
