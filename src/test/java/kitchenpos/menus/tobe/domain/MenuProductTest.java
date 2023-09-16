package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuProductTest {

    @DisplayName("MenuProducts를 생성할 수 있다.")
    @Test
    void create() {
        final MenuProduct menuProduct = new MenuProduct(1L, UUID.randomUUID(), BigDecimal.valueOf(10000L), 1L);
        assertAll(
                () -> assertEquals(menuProduct.getSeq(), 1L),
                () -> assertNotNull(menuProduct.getProductId()),
                () -> assertEquals(menuProduct.getQuantity(), 1L),
                () -> assertEquals(menuProduct.getPrice(), BigDecimal.valueOf(10000L))
        );
    }

    @DisplayName("MenuProduct 생성 시 수량이 0보다 작으면 예외가 발생한다.")
    @Test
    void createWithNegativeQuantity() {
        assertThatThrownBy(() -> new MenuProduct(1L, UUID.randomUUID(), BigDecimal.valueOf(10000L), -1L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("MenuProduct 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        final MenuProduct menuProduct = new MenuProduct(1L, UUID.randomUUID(), BigDecimal.valueOf(10000L), 1L);
        menuProduct.changePrice(BigDecimal.valueOf(20000));
        assertEquals(menuProduct.getPrice(), BigDecimal.valueOf(20000));
    }

    @DisplayName("MenuProduct의 가격과 수량을 곱합 결과인 amount를 계산할 수 있다.")
    @Test
    void calculateAmount() {
        final MenuProduct menuProduct = new MenuProduct(1L, UUID.randomUUID(), BigDecimal.valueOf(10000L), 2L);
        assertEquals(menuProduct.calculateAmount(), BigDecimal.valueOf(20000L));
    }

    @DisplayName("MenuProducts의 productId와 동일한지 여부를 확인할 수 있다.")
    @Test
    void isSameProductId() {
        final UUID productId = UUID.randomUUID();
        final MenuProduct menuProduct = new MenuProduct(1L, productId, BigDecimal.valueOf(10000L), 2L);
        assertThat(menuProduct.isSameProductId(productId)).isTrue();
    }
}
