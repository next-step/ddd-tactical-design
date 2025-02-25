package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    @DisplayName("상품의 이름이 없으면 예외가 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", " ", "   "})
    void 상품의_이름이_없으면_안된다(final String invalidProductName) {
        // given & when & then
        assertThatThrownBy(() -> new ProductName(invalidProductName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름이 존재해야 한다.");
     }
}