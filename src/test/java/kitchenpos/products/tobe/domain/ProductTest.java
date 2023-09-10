package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.Menu;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ProductTest {

    @Test
    @DisplayName("상품을 생성할 수 있다.")
    void create() {
        // given
        UUID id = UUID.randomUUID();
        String name = "상품";
        BigDecimal price = BigDecimal.valueOf(10_000L);

        // when
        Product actual = Product.create(id, name, price);

        // then
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(new Name(name)),
            () -> assertThat(actual.getPrice()).isEqualTo(new Price(price))
        );
    }

    @Test
    @DisplayName("상품의 가격을 바꾼다")
    void changePrice() {
        // give
        Menu menu = menu();
        Product product = menu.getMenuProducts().get(0).getProduct();

        // when
        product.changePrice(BigDecimal.valueOf(15_000L), List.of(menu));

        // then
        assertAll(
            () -> assertThat(product.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(15_000L))),
            () -> assertThat(menu.isDisplayed()).isTrue()
        );
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        // give
        Menu menu = menu();
        Product product = menu.getMenuProducts().get(0).getProduct();

        // when
        product.changePrice(BigDecimal.valueOf(8_000L), List.of(menu));

        // then
        assertAll(
            () -> assertThat(product.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(8_000L))),
            () -> assertThat(menu.isDisplayed()).isFalse()
        );
    }
}
