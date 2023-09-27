package kitchenpos.menu.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.Fixtures;
import kitchenpos.product.tobe.domain.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

class MenuProductsTest {

    @DisplayName("메뉴상품 컬렉션을 생성한다")
    @Test
    void testInitMenuProducts() {
        // given
        var value = List.of(Fixtures.menuProduct());

        // when // then
        assertDoesNotThrow(() -> new MenuProducts(value));
    }

    @DisplayName("값이 유효하지 않으면 메뉴상품 컬렉션을 생성할 수 없다")
    @ParameterizedTest
    @EmptySource
    @NullSource
    void testInitMenuProductsIfNotValidateValue(List<MenuProduct> value) {
        // when // then
        assertThatThrownBy(() -> new MenuProducts(value))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴상품 컬렉션의 총 가격 합을 구한다")
    @Test
    void testSumOfMenuProductPrice() {
        // given
        var sut = Fixtures.menuProducts(Fixtures.menuProduct(10_000L, 2), Fixtures.menuProduct(5_000L, 1));

        // when
        var actual = sut.sumOfMenuProductPrice();

        // then
        assertThat(actual).isEqualTo(new MenuPrice(BigDecimal.valueOf(25_000L)));
    }

    @DisplayName("메뉴 상품의 가격을 수정한다")
    @Test
    void testChangeMenuProductPrice() {
        // given
        var menuProduct = Fixtures.menuProduct();

        // when
        menuProduct.changePrice(MenuPrice.of(BigDecimal.ONE));

        // then
        assertThat(menuProduct.getPrice()).isEqualTo(new ProductPrice(BigDecimal.ONE));
    }
}
