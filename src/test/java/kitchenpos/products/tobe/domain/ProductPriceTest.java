package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductPriceTest {

    @Test
    void 상품가격_객체_생성() {
        ProductPrice productPrice = new ProductPrice(BigDecimal.TEN);
        assertThat(productPrice).isEqualTo(new ProductPrice(BigDecimal.TEN));
    }

    @Test
    void 상품의_가격이_0보다_작으면_IllegalArgumentException이_발생한다() {
        assertThatThrownBy(() -> new Product("후라이드", BigDecimal.valueOf(-10_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 상품의_가격이_null_이면_IllegalArgumentException이_발생한다() {
        assertThatThrownBy(() -> new Product("후라이드", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
