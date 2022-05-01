package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuProductsTest {

    @DisplayName("메뉴상품을 생성해보자")
    @Test
    void create() {
        Product product = product();
        List<Product> products = Arrays.asList(product);
        List<MenuProductDto> menuProductDtos = Arrays.asList(menuProductDto(product.getId(), 1L));
        MenuProducts menuProducts = new MenuProducts(menuProductDtos, products, new Price(product.getPrice()));

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
        List<MenuProductDto> menuProductDtos =
                Arrays.asList(menuProductDto(UUID.randomUUID(), 1L), menuProductDto(product.getId(), 1L));
        assertThatThrownBy(
                () -> new MenuProducts(menuProductDtos, products, new Price(product.getPrice()))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격은 메뉴상품의 총합보다 작거나 같아야한다.")
    @ParameterizedTest
    @CsvSource(value = {"18000:19000", "1000:5000", "100:110"}, delimiter = ':')
    void menuPriceNotExpesiveThanMenuProduct(long productPrice, long menuPrice) {
        Product product = product("양념치킨", productPrice);
        List<Product> products = Arrays.asList(product);
        List<MenuProductDto> menuProductDtos = Arrays.asList(menuProductDto(product.getId(), 1L));

        assertThatThrownBy(
                () -> new MenuProducts(menuProductDtos, products, new Price(BigDecimal.valueOf(menuPrice)))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격이 메뉴상품의 가격보다 비싸지 않을경우를 테스트해보자")
    @ParameterizedTest
    @CsvSource(value = {"18000:19000", "5000:5000", "100:100"}, delimiter = ':')
    void isNotExpensivePrice(long menuPrice, long productPrice) {
        Product product = product("양념치킨", productPrice);
        List<Product> products = Arrays.asList(product);
        List<MenuProductDto> menuProductDtos = Arrays.asList(menuProductDto(product.getId(), 1L));
        MenuProducts menuProducts = new MenuProducts(menuProductDtos, products, new Price(product.getPrice()));

        assertThat(menuProducts.isExpensiveMenuPrice(new Price(BigDecimal.valueOf(menuPrice)), products)).isFalse();
    }

    @DisplayName("메뉴의 가격이 메뉴상품의 가격보다 비쌀경우를 테스트해보자")
    @ParameterizedTest
    @CsvSource(value = {"19000:18000", "5000:1000", "110:100"}, delimiter = ':')
    void isExpensivePrice(long menuPrice, long productPrice) {
        Product product = product("양념치킨", productPrice);
        List<Product> products = Arrays.asList(product);
        List<MenuProductDto> menuProductDtos = Arrays.asList(menuProductDto(product.getId(), 1L));
        MenuProducts menuProducts = new MenuProducts(menuProductDtos, products, new Price(product.getPrice()));

        assertThat(menuProducts.isExpensiveMenuPrice(new Price(BigDecimal.valueOf(menuPrice)), products)).isTrue();
    }

}
