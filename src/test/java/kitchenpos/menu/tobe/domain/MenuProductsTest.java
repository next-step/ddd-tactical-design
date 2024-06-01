package kitchenpos.menu.tobe.domain;

import kitchenpos.menu.tobe.domain.menu.MenuProduct;
import kitchenpos.menu.tobe.domain.menu.MenuProducts;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

class MenuProductsTest {
    @Test
    @DisplayName("메뉴에 속한 상품이 비어있다면 예외가 발생한다.")
    void test1() {
        Assertions.assertThatThrownBy(() -> new MenuProducts(null, BigDecimal.ZERO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격이 총 상품 가격을 초과하면 예외가 발생한다.")
    void testPriceExceedsTotalProductPrice() {
        List<MenuProduct> menuProductList = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), 1L, BigDecimal.valueOf(1000)),
                new MenuProduct(UUID.randomUUID(), 2L, BigDecimal.valueOf(2000))
        );

        BigDecimal menuPrice = BigDecimal.valueOf(6000);

        Assertions.assertThatThrownBy(() -> new MenuProducts(menuProductList, menuPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격이 총 상품 가격을 초과하지 않으면 정상적으로 생성할 수 있다.")
    void testPriceWithinTotalProductPrice() {
        List<MenuProduct> menuProductList = Arrays.asList(
                new MenuProduct(UUID.randomUUID(), 1L, BigDecimal.valueOf(1000)),
                new MenuProduct(UUID.randomUUID(), 2L, BigDecimal.valueOf(2000))
        );
        BigDecimal menuPrice = BigDecimal.valueOf(5000);

        MenuProducts menuProducts = new MenuProducts(menuProductList,menuPrice);

        Assertions.assertThat(menuProducts).isNotNull();
        Assertions.assertThat(menuProducts.getMenuProducts()).isEqualTo(menuProductList);
    }
}