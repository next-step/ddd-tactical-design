package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.products.exception.InvalidProductNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    private ProductProfanityCheckClient productProfanityCheckClient;

    @BeforeEach
    void setUp() {
        productProfanityCheckClient = new FakeProductProfanityCheckClient();
    }

    @DisplayName("상품 이름은 Null 일 수 없다.")
    @Test
    void nullException() {
        assertThatThrownBy(() -> new ProductName(null, productProfanityCheckClient))
            .isExactlyInstanceOf(InvalidProductNameException.class);
    }

    @DisplayName("상품의 이름은 비속어를 포함할 수 없다.")
    @ValueSource(strings = {"비속어", "욕설"})
    @ParameterizedTest
    void profanityException(String profanity) {
        assertThatThrownBy(() -> new ProductName(profanity, productProfanityCheckClient))
            .isExactlyInstanceOf(InvalidProductNameException.class);
    }
}
