package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    @Test
    @DisplayName("상품명이 null이거나 빈 문자열일 경우 예외가 발생한다.")
    void createProductNameWithNullOrEmpty() {
        // given
        String name = null;

        // when, then
        assertThatThrownBy(() -> new ProductName(name, word -> false))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명에 욕설이 포함되어 있을 경우 예외가 발생한다.")
    void createProductNameWithProfanity() {
        // given
        String name = "욕설";

        // when, then
        assertThatThrownBy(() -> new ProductName(name, word -> true))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명을 생성한다.")
    void createProductName() {
        // given
        String name = "상품명";

        // when
        ProductName productName = new ProductName(name, word -> false);

        // then
        assertThat(productName.value()).isEqualTo(name);
    }
}
