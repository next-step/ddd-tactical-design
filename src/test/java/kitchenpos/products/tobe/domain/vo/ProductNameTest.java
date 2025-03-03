package kitchenpos.products.tobe.domain.vo;

import kitchenpos.products.tobe.domain.exception.InvalidProductException;
import kitchenpos.products.tobe.domain.vo.ProductName;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    @DisplayName("상품의 이름이 존재하지 않으면 안된다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "   "})
    void 상품의_이름이_존재하지_않으면_안된다(final String invalidProductName) {
        // given & when & then
        assertThatThrownBy(() -> new ProductName(invalidProductName))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("상품의 이름이 존재해야 한다.");
    }
}
