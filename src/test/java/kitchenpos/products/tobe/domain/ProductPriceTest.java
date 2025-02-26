package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.exception.InvalidProductException;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

class ProductPriceTest {

    @Test
    @DisplayName("상품의 가격이 존재하지 않으면 예외가 발생한다.")
    void 상품의_가격이_존재해야_한다() {
        // given & when & then
        assertThatThrownBy(() -> new ProductPrice(null))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품의 가격은 존재해야 한다.");
    }

    @DisplayName("상품의 가격이 0보다 작으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1, -100, -99999})
    void 상품의_가격이_0보다_커야한다(final long negativePrice) {
        // given
        BigDecimal price = BigDecimal.valueOf(negativePrice);

        // when & then
        assertThatThrownBy(() -> new ProductPrice(price))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품의 가격은 0보다 커야 한다.");
    }


}
