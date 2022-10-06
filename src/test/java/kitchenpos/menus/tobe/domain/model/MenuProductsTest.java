package kitchenpos.menus.tobe.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    @DisplayName("메뉴 상품들의 가격 총합 구하기")
    @Test
    void amounts() {
        MenuProducts menuProducts = new MenuProducts(List.of(
                new MenuProduct(UUID.randomUUID(), 1, 5_000L),
                new MenuProduct(UUID.randomUUID(), 2, 10_000L)
        ));

        assertThat(menuProducts.amounts()).isEqualTo(25_000L);
    }

    @DisplayName("메뉴 상품 가격 변경하기")
    @Test
    void update() {
        // given
        UUID productId = UUID.randomUUID();

        MenuProducts menuProducts = new MenuProducts(List.of(
                new MenuProduct(productId, 2, 5_000L)
        ));

        // when
        menuProducts.update(productId, 7_000L);

        // then
        assertThat(menuProducts.amounts()).isEqualTo(14_000L);
    }

    @DisplayName("존재하지 않는 메뉴 상품 가격 변경하기")
    @Test
    void update_NotExistingProduct() {
        // given
        MenuProducts menuProducts = new MenuProducts(List.of(
                new MenuProduct(UUID.randomUUID(), 2, 5_000L)
        ));

        // expected
        assertThatThrownBy(() -> menuProducts.update(UUID.randomUUID(), 7_000L))
                .isInstanceOf(IllegalStateException.class);
    }
}
