package kitchenpos.menus.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuTest {

    @DisplayName("메뉴를 생성해보자")
    @Test
    void create() {
        UUID menuId = UUID.randomUUID();
        UUID menuGroupId = menuGroup().getId();
        String menuName = "후라이드+후라이드";
        BigDecimal price = BigDecimal.valueOf(20000L);

        Menu menu = new Menu(new FakePurgomalumClient(), menuId, menuName, price, menuGroupId, true, new MenuProducts(Arrays.asList(menuProduct())));

        assertThat(menu).isNotNull();
        assertAll(
                () -> assertThat(menu.getId()).isEqualTo(menuId),
                () -> assertThat(menu.getName()).isEqualTo(menuName),
                () -> assertThat(menu.getMenuGroupId()).isEqualTo(menuGroupId),
                () -> assertThat(menu.getMenuProducts()).hasSize(1),
                () -> assertThat(menu.getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("메뉴의 가격을 변경해보자")
    @ParameterizedTest
    @CsvSource(value = {"18000:19000", "1000:5000", "1000:1000"}, delimiter = ':')
    void changePrice(long menuPrice, long productPrice) {
        Price price = new Price(BigDecimal.valueOf(menuPrice));
        Product product = product("양념치킨", productPrice);
        menuProduct(product, 1L);
        Menu menu = new Menu(new FakePurgomalumClient(), UUID.randomUUID(), "핫한치킨", BigDecimal.valueOf(productPrice), menuGroup().getId(), true, new MenuProducts(Arrays.asList(menuProduct(product, 1L))));

        menu.changePrice(price, Arrays.asList(product));

        assertThat(menu.getPrice()).isEqualTo(price.getPrice());
    }

    @DisplayName("가격의합이 메뉴가 더 크면 불가능하다")
    @ParameterizedTest
    @CsvSource(value = {"19000:18000", "5000:1000", "2000:1900"}, delimiter = ':')
    void impossibleChangePrice(long menuPrice, long productPrice) {
        Price price = new Price(BigDecimal.valueOf(menuPrice));
        Product product = product("양념치킨", productPrice);
        menuProduct(product, 1L);
        Menu menu = new Menu(new FakePurgomalumClient(), UUID.randomUUID(), "핫한치킨", BigDecimal.valueOf(productPrice), menuGroup().getId(), true, new MenuProducts(Arrays.asList(menuProduct(product, 1L))));

        assertThatThrownBy(
                () -> menu.changePrice(price, Arrays.asList(product))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 노출해보자")
    @ParameterizedTest
    @CsvSource(value = {"18000:19000", "1000:5000", "1000:1000"}, delimiter = ':')
    void show(long menuPrice, long productPrice) {
        Product product = product("양념치킨", productPrice);
        menuProduct(product, 1L);
        Menu menu = new Menu(new FakePurgomalumClient(), UUID.randomUUID(), "인기치킨", BigDecimal.valueOf(menuPrice), menuGroup().getId(), true, new MenuProducts(Arrays.asList(menuProduct(product, 1L))));

        menu.show(Arrays.asList(product));

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("가격의 합이 메뉴가 더 크면 불가능하다")
    @ParameterizedTest
    @CsvSource(value = {"19000:18000", "6000:5000", "2000:1000"}, delimiter = ':')
    void impossibleShow(long menuPrice, long productPrice) {
        Product product = product("양념치킨", productPrice);
        menuProduct(product, 1L);
        Menu menu = new Menu(new FakePurgomalumClient(), UUID.randomUUID(), "인기치킨", BigDecimal.valueOf(menuPrice), menuGroup().getId(), true, new MenuProducts(Arrays.asList(menuProduct(product, 1L))));

        assertThatThrownBy(
                () -> menu.show(Arrays.asList(product))
        ).isInstanceOf(IllegalStateException.class);

        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 미노출 상태로 변경해보자")
    @Test
    void hide() {
        Menu menu = new Menu(new FakePurgomalumClient(), UUID.randomUUID(), "후라이드+후라이드", BigDecimal.valueOf(20000L), menuGroup().getId(), true, new MenuProducts(Arrays.asList(menuProduct())));
        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }
}
