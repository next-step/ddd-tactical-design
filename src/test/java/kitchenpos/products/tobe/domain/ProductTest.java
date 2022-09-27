package kitchenpos.products.tobe.domain;

import static kitchenpos.global.TobeFixtures.name;
import static kitchenpos.global.TobeFixtures.price;
import static kitchenpos.global.TobeFixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.global.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("상품을 등록할 수 있다.")
    @Test
    void create() {
        Product product = product("후라이드");

        assertThat(product).isNotNull();
        assertAll(
                () -> assertThat(product.getId()).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(name("후라이드")),
                () -> assertThat(product.getPrice()).isEqualTo(price(BigDecimal.ZERO))
        );
    }

    @DisplayName("가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        Product product = product("후라이드");
        Price expected = price(BigDecimal.TEN);

        product.changePrice(expected);

        assertThat(product.getPrice()).isEqualTo(expected);
    }
}
