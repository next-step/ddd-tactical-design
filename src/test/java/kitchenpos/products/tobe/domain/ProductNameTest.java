package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductNameTest {

    @DisplayName("상품 이름을 생성한다.")
    @Test
    void create01() {
        ProductName name = new ProductName("후라이드 치킨");

        assertThat(name).isEqualTo(new ProductName("후라이드 치킨"));
    }

    @DisplayName("상품 이름은 비어있을 수 없다.")
    @Test
    void create02() {
        assertThatThrownBy(() -> new ProductName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름은 비어 있을 수 없습니다.");
    }
}
