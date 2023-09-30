package kitchenpos.product.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import kitchenpos.application.FakeProfanityClient;
import kitchenpos.common.profanity.ProfanityClient;
import kitchenpos.product.tobe.domain.service.ProductNameNormalPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    private ProfanityClient profanityClient;

    @BeforeEach
    void setUp() {
        profanityClient = new FakeProfanityClient();
    }

    @DisplayName("상품 이름 객체를 생성한다")
    @ValueSource(strings = {"후라이드 치킨", "양념 치킨", "갈릭 치킨"})
    @ParameterizedTest
    void testInitProductName(String value) {
        // when // then
        assertDoesNotThrow(() -> ProductName.of(value, new ProductNameNormalPolicy(profanityClient)));
    }

    @DisplayName("상품 이름이 올바르지 않으면 상품 이름 객체를 생성할 수 없다")
    @ValueSource(strings = {""})
    @NullSource
    @ParameterizedTest
    void testInitProductNameIfNotValidName(String value) {
        // when // then
        assertThatThrownBy(() -> ProductName.of(value, new ProductNameNormalPolicy(profanityClient)))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
