package kitchenpos.menu.tobe.domain;

import kitchenpos.menu.tobe.domain.menu.MenuProduct;
import kitchenpos.menu.tobe.domain.menu.MenuProducts;
import kitchenpos.menu.tobe.domain.menu.validate.ProductValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.willThrow;

@ExtendWith(MockitoExtension.class)
class MenuProductsTest {
    @Mock
    private ProductValidator productValidator;

    private List<MenuProduct> createMenuProductList() {
        return Arrays.asList(
                new MenuProduct(UUID.randomUUID(), 1L, BigDecimal.valueOf(1000)),
                new MenuProduct(UUID.randomUUID(), 2L, BigDecimal.valueOf(2000))
        );
    }

    @Test
    @DisplayName("메뉴에 속한 상품이 비어있다면 예외가 발생한다.")
    void test1() {
        Assertions.assertThatThrownBy(() -> new MenuProducts(null, BigDecimal.ZERO, productValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격이 총 상품 가격을 초과하면 예외가 발생한다.")
    void test2() {
        List<MenuProduct> menuProductList = createMenuProductList();
        BigDecimal menuPrice = BigDecimal.valueOf(6000);

        Assertions.assertThatThrownBy(() -> new MenuProducts(menuProductList, menuPrice, productValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 존재 유효성 검사에서 예외가 발생하면 메뉴를 생성할 수 없다.")
    void test3() {
        List<MenuProduct> menuProductList = createMenuProductList();
        BigDecimal menuPrice = BigDecimal.valueOf(3000);

        willThrow(new IllegalArgumentException()).given(productValidator).validateProductExistence(menuProductList);

        Assertions.assertThatThrownBy(() -> new MenuProducts(menuProductList, menuPrice, productValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격이 총 상품 가격을 초과하지 않으면 정상적으로 생성할 수 있다.")
    void test4() {
        List<MenuProduct> menuProductList = createMenuProductList();
        BigDecimal menuPrice = BigDecimal.valueOf(5000);

        MenuProducts menuProducts = new MenuProducts(menuProductList, menuPrice, productValidator);

        Assertions.assertThat(menuProducts).isNotNull();
        Assertions.assertThat(menuProducts.getMenuProducts()).isEqualTo(menuProductList);
    }

    @Test
    @DisplayName("상품 가격 변경 시 존재하지 않는 상품 ID가 주어지면 예외가 발생한다.")
    void test5() {
        List<MenuProduct> menuProductList = createMenuProductList();
        BigDecimal menuPrice = BigDecimal.valueOf(5000);
        MenuProducts menuProducts = new MenuProducts(menuProductList, menuPrice, productValidator);

        UUID nonExistentProductId = UUID.randomUUID();
        BigDecimal newPrice = BigDecimal.valueOf(3000);

        Assertions.assertThatThrownBy(() -> menuProducts.changeMenuProductPrice(nonExistentProductId, newPrice))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품 가격 변경 시 존재하는 상품 ID가 주어지면 가격이 변경된다.")
    void test6() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        List<MenuProduct> menuProductList = Arrays.asList(
                new MenuProduct(productId1, 1L, BigDecimal.valueOf(1000)),
                new MenuProduct(productId2, 2L, BigDecimal.valueOf(2000))
        );
        BigDecimal menuPrice = BigDecimal.valueOf(5000);
        MenuProducts menuProducts = new MenuProducts(menuProductList, menuPrice, productValidator);

        BigDecimal newPrice = BigDecimal.valueOf(3000);
        menuProducts.changeMenuProductPrice(productId1, newPrice);

        Assertions.assertThat(menuProducts.getMenuProducts().stream()
                .filter(menuProduct -> menuProduct.isSameProductId(productId1))
                .findFirst().get().getPrice()).isEqualByComparingTo(newPrice);
    }
}