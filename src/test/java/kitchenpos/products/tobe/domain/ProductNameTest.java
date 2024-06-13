package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ProductNameTest {

    @Test
    void 상품이름_객체_생성() {
        ProductName name = new ProductName("후라이드");
        assertThat(name).isEqualTo(new ProductName("후라이드"));
    }

    @Test
    void 상품의_이름이_null_이면_IllegalArgumentException이_발생한다() {
        assertThatThrownBy(() -> new Product(null, BigDecimal.valueOf(10_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
