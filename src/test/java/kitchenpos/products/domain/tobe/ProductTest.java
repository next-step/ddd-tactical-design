package kitchenpos.products.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThatThrownBy(() -> new Product("후라이드", null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 가격이_마이너스인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(
                () -> new Product("후라이드", BigDecimal.valueOf(-20_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 이름이_null인_Product를_생성하면_예외를_던진다() {
        assertThatThrownBy(() -> new Product(null, BigDecimal.valueOf(20_000L)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Product의_가격을_변경할_수_있다() {
        Product product = new Product("치킨", BigDecimal.valueOf(20_000L));

        product.changePrice(new ProductPrice(BigDecimal.valueOf(25_000L)));

        assertThat(product.isSamePrice(new ProductPrice(BigDecimal.valueOf(25_000L)))).isTrue();
    }

    @Test
    void Product의_가격을_null로_변경하면_예외를_던진다() {
        Product product = new Product("치킨", BigDecimal.valueOf(20_000L));

        assertThatThrownBy(() -> product.changePrice(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void Product의_가격을_마이너스로_변경하면_예외를_던진다() {
        Product product = new Product("치킨", BigDecimal.valueOf(20_000L));

        assertThatThrownBy(() -> product.changePrice(new ProductPrice(BigDecimal.valueOf(-20_000L))))
                .isInstanceOf(IllegalArgumentException.class);
    }
}