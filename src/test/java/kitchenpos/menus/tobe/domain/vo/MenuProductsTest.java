package kitchenpos.menus.tobe.domain.vo;

import static java.util.List.of;
import static kitchenpos.menus.tobe.MenuFixtures.menuProduct;
import static kitchenpos.menus.tobe.MenuFixtures.price;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProducts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuProductsTest {

    @DisplayName("상품 메뉴 일급 컬렉션을 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new MenuProducts(of(
                menuProduct(1, 1_000)))
        ).doesNotThrowAnyException();
    }

    @DisplayName("메뉴 상품들의 합계를 반환한다.")
    @Test
    void sum() {
        MenuProducts menuProducts = new MenuProducts(of(
                menuProduct(1, 1),
                menuProduct(2, 1),
                menuProduct(3, 0)
        ));

        assertThat(menuProducts.sum())
                .isEqualTo(price(3));
    }

    @DisplayName("메뉴 상품 일급 컬렉션은 반드시 하나 이상의 메뉴 상품을 갖는다.")
    @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
    @NullAndEmptySource
    void error1(List<MenuProduct> values) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuProducts(values));
    }
}
