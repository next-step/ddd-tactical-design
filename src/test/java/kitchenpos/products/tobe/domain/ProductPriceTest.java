package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductPriceTest {
    @Test
    @DisplayName("상품의 가격이 0원 이상이어야 한다")
    void constructor01() {
        ProductPrice productPrice = new ProductPrice(0);

        assertThat(productPrice).isEqualTo(new ProductPrice(0));
    }

    @Test
    @DisplayName("상품의 가격이 0원 미만일 수 없다")
    void constructor02() {
        assertThatThrownBy(() ->
                new ProductPrice(-1)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
