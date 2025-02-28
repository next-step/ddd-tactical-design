package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.exception.MenuException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    private final UUID productId1 = UUID.randomUUID();
    private final UUID productId2 = UUID.randomUUID();
    private final UUID nonExistingProductId = UUID.randomUUID();

    @Test
    @DisplayName("MenuProducts 객체를 생성할 수 있다")
    void create() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);

        // when
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // then
        assertThat(menuProducts.getMenuProducts()).hasSize(2);
        assertThat(menuProducts.getProductCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("MenuProducts 객체를 리스트로 생성할 수 있다")
    void createFromList() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);

        // when
        MenuProducts menuProducts = MenuProducts.from(Arrays.asList(menuProduct1, menuProduct2));

        // then
        assertThat(menuProducts.getMenuProducts()).hasSize(2);
        assertThat(menuProducts.getProductCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("비어있는 상품 목록으로 생성하면 예외가 발생한다")
    void createWithEmptyList() {
        // when & then
        assertThatThrownBy(() -> MenuProducts.from(Collections.emptyList()))
                .isInstanceOf(MenuException.class);
    }

    @Test
    @DisplayName("null 상품 목록으로 생성하면 예외가 발생한다")
    void createWithNullList() {
        // when & then
        assertThatThrownBy(() -> MenuProducts.from((ArrayList<MenuProduct>) null))
                .isInstanceOf(MenuException.class);
    }

    @Test
    @DisplayName("상품 가격의 합계가 메뉴 가격보다 작거나 같은지 확인할 수 있다")
    void isTotalPriceLessThanOrEqualTo() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);  // 10000원
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);  // 8000원
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // 총 가격: 18000원

        // when & then
        assertThat(menuProducts.isTotalPriceLessThanOrEqualTo(MenuPrice.from(20000L))).isTrue();
        assertThat(menuProducts.isTotalPriceLessThanOrEqualTo(MenuPrice.from(18000L))).isTrue();
        assertThat(menuProducts.isTotalPriceLessThanOrEqualTo(MenuPrice.from(17000L))).isFalse();
    }

    @Test
    @DisplayName("상품 가격의 합계를 계산할 수 있다")
    void calculateTotalPrice() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);  // 10000원
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);  // 8000원
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // when
        Long totalPrice = menuProducts.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(18000L);  // 10000 + 8000
    }

    @Test
    @DisplayName("특정 상품 ID가 포함되어 있는지 확인할 수 있다")
    void containsProduct() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // when & then
        assertThat(menuProducts.containsProduct(productId1)).isTrue();
        assertThat(menuProducts.containsProduct(productId2)).isTrue();
        assertThat(menuProducts.containsProduct(nonExistingProductId)).isFalse();
    }

    @Test
    @DisplayName("상품 ID로 MenuProduct를 찾을 수 있다")
    void findProduct() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // when
        MenuProduct foundProduct = menuProducts.findProduct(productId1);

        // then
        assertThat(foundProduct).isEqualTo(menuProduct1);
    }

    @Test
    @DisplayName("존재하지 않는 상품 ID로 찾으면 예외가 발생한다")
    void findNonExistingProduct() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // when & then
        assertThatThrownBy(() -> menuProducts.findProduct(nonExistingProductId))
                .isInstanceOf(MenuException.class);
    }

    @Test
    @DisplayName("특정 상품의 수량이 필요한 수량을 충족하는지 확인할 수 있다")
    void hasEnoughQuantityOf() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // when & then
        assertThat(menuProducts.hasEnoughQuantityOf(productId1, 1)).isTrue();
        assertThat(menuProducts.hasEnoughQuantityOf(productId1, 2)).isTrue();
        assertThat(menuProducts.hasEnoughQuantityOf(productId1, 3)).isFalse();
        assertThat(menuProducts.hasEnoughQuantityOf(productId2, 1)).isTrue();
        assertThat(menuProducts.hasEnoughQuantityOf(productId2, 2)).isFalse();
    }

    @Test
    @DisplayName("상품 ID로 MenuProduct를 가져올 수 있다")
    void getProduct() {
        // given
        MenuProduct menuProduct1 = new MenuProduct(1L, 2, 5000L, productId1);
        MenuProduct menuProduct2 = new MenuProduct(2L, 1, 8000L, productId2);
        MenuProducts menuProducts = MenuProducts.from(menuProduct1, menuProduct2);

        // when
        MenuProduct product = menuProducts.getProduct(productId1);

        // then
        assertThat(product).isSameAs(menuProduct1);
    }
}