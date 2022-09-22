package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {
    @DisplayName("유효한 인자인 경우 정상적으로 객체가 생성된다.")
    @Test
    void create() {
        // given
        String displayedName = "displayedName";
        BigDecimal price = BigDecimal.TEN;

        // when
        Product product = new Product(displayedName, price);

        // then
        assertAll(
            () -> assertThat(product.getId()).isNotNull(),
            () -> assertThat(product.getDisplayedName()).isEqualTo(new DisplayedName(displayedName)),
            () -> assertThat(product.getPrice()).isEqualTo(new Price(price))
        );
    }

    @DisplayName("유효한 가격인 경우 상품의 가격을 변경한다.")
    @Test
    void changePrice() {
        // given
        Product product = new Product("displayedName", BigDecimal.TEN);

        // when
        product.changePrice(BigDecimal.ZERO);

        // then
        assertThat(product.getPrice()).isEqualTo(new Price(BigDecimal.ZERO));
    }
}
