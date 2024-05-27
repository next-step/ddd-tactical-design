package kitchenpos.domain.product.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ProductNameTest {

    @DisplayName("ProductName을 생성한다")
    @Test
    void constructor() {
        String name = "상품명";
        ProductName productName = new ProductName(name);
        assertThat(productName).isEqualTo(new ProductName(name));
    }

    @DisplayName("ProductName 이름이 Null이면 생성을 실패한다")
    @Test
    void constructor_null_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ProductName(null));
    }

    @DisplayName("ProductName 이름에 비속어가 포함됐으면 생성을 실패한다")
    @Test
    void constructor_blackWord_fail() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ProductName("비속어가포함된", (text) -> true));
    }
}
