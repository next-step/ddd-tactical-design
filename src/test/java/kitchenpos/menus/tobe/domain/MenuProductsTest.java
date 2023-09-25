package kitchenpos.menus.tobe.domain;

import kitchenpos.NewFixtures;
import kitchenpos.common.domain.Price;
import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.NewProduct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuExceptionMessage.EMPTY_MENU_PRODUCT;
import static kitchenpos.menus.exception.MenuExceptionMessage.MENU_PRICE_MORE_PRODUCTS_SUM;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("메뉴상품목록 테스트")
class MenuProductsTest {

    private final NewProduct newProduct1 = NewFixtures.newProduct(1_000L);
    private final NewProduct newProduct2 = NewFixtures.newProduct(2_000L);
    private final NewProduct newProduct3 = NewFixtures.newProduct(3_000L);

    @BeforeEach
    void setUp() {

    }

    @DisplayName("메뉴상품목록 생성 성공")
    @Test
    void create() {
        MenuProducts menuProducts = MenuProducts.create(List.of(MenuProduct.create(newProduct1, 1L)));

        assertThat(menuProducts).isEqualTo(MenuProducts.create(List.of(MenuProduct.create(newProduct1, 1L))));
    }

    @DisplayName("메뉴상품목록이 비어있으면 예외를 반환한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create_failed(List<MenuProduct> input) {
        assertThatThrownBy( () -> MenuProducts.create(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(EMPTY_MENU_PRODUCT);
    }

    @DisplayName("메뉴상품의 상품ID를 반환한다.")
    @Test
    void getMenuProductIds() {
        List<NewProduct> products = List.of(newProduct1, newProduct2, newProduct3);
        List<MenuProduct> newMenuProducts = List.of(
                MenuProduct.create(products.get(0), 1L),
                MenuProduct.create(products.get(1), 5L),
                MenuProduct.create(products.get(2), 10L));
        MenuProducts menuProducts = MenuProducts.create(newMenuProducts);

        List<UUID> result = menuProducts.getMenuProductIds();

        assertThat(result.size()).isEqualTo(3);
    }

    @DisplayName("메뉴가격이 메뉴상품목록 가격의 합보다 크면 예외를 반환한다.")
    @Test
    void validateMenuPrice() {
        MenuProducts menuProducts = MenuProducts.create(List.of(
                MenuProduct.create(newProduct1, 1L),
                MenuProduct.create(newProduct2, 2L)
        ));

        assertThatThrownBy( () -> menuProducts.validateMenuPrice(Price.of(BigDecimal.valueOf(10_000L))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(MENU_PRICE_MORE_PRODUCTS_SUM);
    }

}
