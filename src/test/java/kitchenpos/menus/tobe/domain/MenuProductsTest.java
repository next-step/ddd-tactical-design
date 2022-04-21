package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.ui.dto.MenuProductRequest;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menuProductRequest;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductsTest {

    @DisplayName("메뉴상품을 생성해보자")
    @Test
    void create() {
        Product product = product();
        List<Product> products = Arrays.asList(product);
        List<MenuProductRequest> menuProductRequests = Arrays.asList(menuProductRequest(product.getId(), 1L));
        MenuProducts menuProducts = new MenuProducts(menuProductRequests, products, new Price(product.getPrice()));

        assertThat(menuProducts).isNotNull();
        assertAll(
                () -> assertThat(menuProducts.getMenuProducts()).isNotNull(),
                () -> assertThat(menuProducts.getMenuProducts()).hasSize(1),
                () -> assertThat(menuProducts.size()).isEqualTo(1)
        );
    }

    @DisplayName("등록하려는 메뉴상품과 상품의 갯수가 다르면 생성할수 없다")
    @Test
    void notEqualProductCount() {
        Product product = product();
        List<Product> products = Arrays.asList(product);
        List<MenuProductRequest> menuProductRequests =
                Arrays.asList(menuProductRequest(UUID.randomUUID(), 1L), menuProductRequest(product.getId(), 1L));
        assertThatThrownBy(
                () -> new MenuProducts(menuProductRequests, products, new Price(product.getPrice()))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격은 메뉴상품의 총합보다 작거나 같아야한다.")
    @ParameterizedTest
    @CsvSource(value = {"18000:19000", "1000:5000", "100:110"}, delimiter = ':')
    void menuPriceNotExpesiveThanMenuProduct(long productPrice, long menuPrice) {
        Product product = product("양념치킨", productPrice);
        List<Product> products = Arrays.asList(product);
        List<MenuProductRequest> menuProductRequests = Arrays.asList(menuProductRequest(product.getId(), 1L));
        assertThatThrownBy(
                () -> new MenuProducts(menuProductRequests, products, new Price(BigDecimal.valueOf(menuPrice)))
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
