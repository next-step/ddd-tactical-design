package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    @Test
    @DisplayName("상품 이름을 생성할 수 있다.")
    void create() {
        // given
        String name = "상품";

        // when
        ProductName actual = new ProductName(name, false);

        // then
        assertThat(actual).isEqualTo(new ProductName(name, false));
    }

    @Test
    @DisplayName("상품의 이름에 비속어는 포함될 수 없다.")
    void containsProfanity() {
        assertThatThrownBy(() -> new ProductName("비속어", true))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품의 이름은 빈 값이 될 수 없다.")
    void emptyValue() {
        assertThatThrownBy(() -> new ProductName(null, false))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
