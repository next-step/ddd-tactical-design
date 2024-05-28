package kitchenpos.domain.menu.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class MenuProductsTest {

    @DisplayName("메뉴 상품 목록을 생성한다")
    @Test
    void constructor() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(1_000), 1);
        MenuProducts menuProducts = new MenuProducts(List.of(menuProduct));
        assertThat(menuProducts.getTotalPrice()).isEqualTo(menuProduct.totalPrice());
    }

    @DisplayName("메뉴 상품 목록이 Null이면 생성을 실패한다")
    @Test
    void constructor_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuProducts(null));
    }

    @DisplayName("메뉴 상품 목록이 빈 값이면 생성을 실패한다")
    @Test
    void constructor_empty_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new MenuProducts(List.of()));
    }
}
