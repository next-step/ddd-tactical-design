package kitchenpos.products.tobe.domain.model.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

class ProductPriceTest {

    @Test
    void 상품가격은_0원_미만일_수_없다() {
        // given
        int price = -1;

        // when & then
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new ProductPrice(price));
    }

    @Test
    void 상품가격은_동등성을_보장한다() {
        // given
        var price1 = new ProductPrice(1);
        var price2 = new ProductPrice(1);
        var price3 = new ProductPrice(2);

        // when & then
        assertAll(
            () -> assertThat(price1).isEqualTo(price2),
            () -> assertThat(price1).isNotEqualTo(price3)
        );
    }
}