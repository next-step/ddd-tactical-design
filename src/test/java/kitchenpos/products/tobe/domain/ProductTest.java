package kitchenpos.products.tobe.domain;

import static kitchenpos.products.tobe.ProductFixtures.product;
import static kitchenpos.products.tobe.ProductFixtures.productName;
import static kitchenpos.products.tobe.ProductFixtures.productPrice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.products.tobe.domain.vo.ProductPrice;
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
                () -> assertThat(product.getName()).isEqualTo(productName("후라이드")),
                () -> assertThat(product.getPrice()).isEqualTo(productPrice(15_000))
        );
    }

    @DisplayName("가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        Product product = product("후라이드", 0);

        ProductPrice expected = productPrice(36_000);
        product.changePrice(expected);

        assertThat(product.getPrice()).isEqualTo(expected);
    }
}
