package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProductNameTest {
    @Test
    @DisplayName("상품의 이름은 1자 이상이어야 한다")
    void constructor01() {
        ProductName productPrice = new ProductName("오이");

        assertThat(productPrice).isEqualTo(new ProductName("오이"));
    }

    @DisplayName("상품의 이름은 공백일 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void constructor02(String value) {
        assertThatThrownBy(() ->
                new ProductName(value)
        ).isInstanceOf(IllegalArgumentException.class);
    }
}
