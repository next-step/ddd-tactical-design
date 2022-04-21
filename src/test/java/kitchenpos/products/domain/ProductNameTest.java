package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.fakeobject.FakeProductPurgomalumClient;
import kitchenpos.products.exception.ProductNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

/**
 * - 상품의 이름이 올바르지 않으면 등록할 수 없다.
 *   - 상품의 이름에는 비속어가 포함될 수 없다.
 */
class ProductNameTest {

    @DisplayName("상품의 이름에는 비속어가 포함될 경우 예외.")
    @Test
    void product_name_contain_profanities() {
        assertThatThrownBy(() -> new ProductName("욕설", new FakeProductPurgomalumClient()))
                .isInstanceOf(ProductNameException.class);
    }

    @DisplayName("상품의 이름이 null 인 경우 예외.")
    @ParameterizedTest
    @NullSource
    void product_name_is_null(String name) {
        assertThatThrownBy(() -> new ProductName(name, new FakeProductPurgomalumClient()))
                .isInstanceOf(ProductNameException.class);
    }
}
