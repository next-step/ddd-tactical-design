package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductNameTest {
    @DisplayName("상품 이름은 비어있을 수 없다.")
    @Test
    void emptyName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName(null))
                .withMessage("상품 이름은 필수값입니다.");
    }

    @DisplayName("적절하지 않은 상품 이름은 validation 에 실패한다.")
    @Test
    void validateName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ProductName("상품 이름").validate(name -> true))
                .withMessage("적절하지 않은 상품 이름입니다.");
    }
}
