package kitchenpos.menus.tobe.domain.vo;

import static java.util.List.of;
import static kitchenpos.menus.tobe.domain.MenuFixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
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
                menuProduct(1, BigDecimal.ONE)))
        ).doesNotThrowAnyException();
    }

    @DisplayName("메뉴 상품들의 합계를 반환한다.")
    @Test
    void sum() {
        MenuProducts menuProducts = new MenuProducts(of(
                menuProduct(1, BigDecimal.ONE),
                menuProduct(2, BigDecimal.ONE),
                menuProduct(3, BigDecimal.ZERO)
        ));

        assertThat(menuProducts.sum())
                .isEqualTo(BigDecimal.valueOf(3));
    }

    @DisplayName("메뉴 상품 일급 컬렉션은 반드시 하나 이상의 메뉴 상품을 갖는다.")
    @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
    @NullAndEmptySource
    void error1(List<MenuProduct> values) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuProducts(values));
    }
}
