package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.vo.MenuProductQuantity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuProductTest {

    @DisplayName("메뉴 상품을 생성한다.")
    @Test
    void create() {
        UUID productId = UUID.randomUUID();
        MenuProductQuantity quantity = new MenuProductQuantity(0);
        BigDecimal productPrice = BigDecimal.ZERO;
        MenuProduct menuProduct = createMenuProduct(productId, quantity, productPrice);

        assertThat(menuProduct).isNotNull();
        assertThat(menuProduct.getAmount()).isNotNull();
    }

    private MenuProduct createMenuProduct(UUID productId, MenuProductQuantity quantity, BigDecimal productPrice) {
        return new MenuProduct(productId, quantity, productPrice);
    }

    @DisplayName("메뉴 상품 예외 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("메뉴 상품의 수량은 0개 이상이어야 한다.")
        @ParameterizedTest
        @ValueSource(longs = -1)
        void error1(long quantity) {
            UUID productId = UUID.randomUUID();
            BigDecimal productPrice = BigDecimal.ZERO;

            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> createMenuProduct(productId, new MenuProductQuantity(quantity), productPrice));
        }

        @DisplayName("메뉴 상품의 상품 가격은 비어 있을 수 없다.")
        @ParameterizedTest
        @NullSource
        void error2(BigDecimal productPrice) {
            UUID productId = UUID.randomUUID();

            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> createMenuProduct(productId, new MenuProductQuantity(0), productPrice));
        }
    }
}
