package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {

    @Test
    @DisplayName("후라이드 상품을 생성한다")
    void success() {
        Product product = new Product("후라이드", BigDecimal.valueOf(10_000L));
        assertThat(product.getName()).isEqualTo(new ProductName("후라이드"));
        assertThat(product.getPrice()).isEqualTo(new ProductPrice(BigDecimal.valueOf(10_000L)));
    }

    @Test
    @DisplayName("상품의 이름이 null이면 IllegalArgumentException이 발생한다")
    void fail() {
        assertThatThrownBy(() -> new Product(null, BigDecimal.valueOf(10_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 가격이 0보다 작으면 IllegalArgumentException이 발생한다")
    void fail2() {
        assertThatThrownBy(() -> new Product("후라이드", BigDecimal.valueOf(-10_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 가격이 null이면 IllegalArgumentException이 발생한다")
    void fail3() {
        assertThatThrownBy(() -> new Product("후라이드", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
