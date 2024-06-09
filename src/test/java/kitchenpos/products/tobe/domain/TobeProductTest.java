package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeProductTest {

    @Test
    @DisplayName("후라이드 상품을 생성한다")
    void success() {
        Product product = new Product("후라이드");
        assertThat(product.getName()).isEqualTo(new ProductName("후라이드"));
    }

    @Test
    @DisplayName("상품의 이름이 null이면 IllegalArgumentException이 발생한다")
    void fail() {
        assertThatThrownBy(() ->new Product(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
