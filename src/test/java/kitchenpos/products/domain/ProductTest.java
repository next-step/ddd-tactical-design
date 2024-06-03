package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Product")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProductTest {

    @Test
    void 가격이_null인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new Product(null, "후라이드", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 가격이_마이너스인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new Product(null, "후라이드", BigDecimal.valueOf(-20_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_null인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new Product(null, null, BigDecimal.valueOf(20_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}