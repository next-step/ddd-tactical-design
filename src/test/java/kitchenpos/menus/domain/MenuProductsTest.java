package kitchenpos.menus.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MenuProductsTest {

    private MenuProducts menuProducts;

    @BeforeEach
    void setup() {
         menuProducts = new MenuProducts(List.of(menuProduct()));
    }

    @Test
    @DisplayName("메뉴 상품 리스트를 생성한다.")
    void createMenuProducts() {
        // when
        MenuProducts menuProducts = new MenuProducts(List.of(menuProduct()));

        // then
        assertThat(menuProducts.menuProducts()).isNotEmpty();
    }

    @ParameterizedTest
    @DisplayName("메뉴 상품 리스트가 null이거나 존재하지 않으면 Exception을 발생 시킨다.")
    @NullSource
    @EmptySource
    void createMenuProducts(List<MenuProduct> menuProductList) {
        // when
        // then
        assertThatThrownBy(() -> new MenuProducts(menuProductList))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 상품 리스트 상품 가격의 합을 가져온다.")
    void menuProductsPriceSum() {
        // when
        BigDecimal menuProductPriceSum = menuProducts.menuProductPriceSum();

        // then
        assertThat(menuProductPriceSum).isNotNull();
    }

}
