package kitchenpos.products.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

class ProductNoTest {

    @DisplayName("상품 식별자는 식별할 수 있는 고유한 값을 갖는다.")
    @RepeatedTest(30)
    void create() {
        ProductNo actual = new ProductNo();

        assertThat(actual).isNotEqualTo(new ProductNo());
    }
}
