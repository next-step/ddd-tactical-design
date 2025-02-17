package kitchenpos.product.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ProductNameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("이름은 비어있거나, null이면 예외가 발생한다.")
    void create_name_exception(String name) {
        // when // then
        assertThatThrownBy(() -> new ProductName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이름을 채워주세요!");
    }
}
