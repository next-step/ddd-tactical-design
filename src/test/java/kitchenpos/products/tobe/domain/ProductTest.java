package kitchenpos.products.tobe.domain;

import static kitchenpos.products.tobe.ProductFixtures.name;
import static kitchenpos.products.tobe.ProductFixtures.price;
import static kitchenpos.products.tobe.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.global.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        Product product = product("후라이드", 15_000);

        assertThat(product).isNotNull();
        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(name("후라이드")),
                () -> assertThat(product.getPrice()).isEqualTo(price(15_000))
        );
    }

    @DisplayName("가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        Product product = product("후라이드", 0);

        Price expected = price(36_000);
        product.changePrice(expected);

        assertThat(product.getPrice()).isEqualTo(expected);
    }
}
