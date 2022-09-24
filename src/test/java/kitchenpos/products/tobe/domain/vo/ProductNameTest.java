package kitchenpos.products.tobe.domain.vo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.EmptyProductNameException;
import kitchenpos.products.tobe.domain.exception.ProfanityProductNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    private PurgomalumClient profanity;

    @BeforeEach
    void setUp() {
        profanity = new FakePurgomalumClient();
    }

    @DisplayName("상품의 이름을 생성할 수 있다.")
    @Test
    void create() {
        ProductName expected = createProductName("gmoon");

        assertThat(expected).isEqualTo(createProductName("gmoon"));
        assertThat(expected.hashCode() == createProductName("gmoon").hashCode())
                .isTrue();
    }

    private ProductName createProductName(String name) {
        return new ProductName(name, profanity);
    }

    @DisplayName("상품 이름 예외 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("공백을 포함할 수 없다")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullAndEmptySource
        void error1(String name) {
            assertThatExceptionOfType(EmptyProductNameException.class)
                    .isThrownBy(() -> createProductName(name));
        }

        @DisplayName("'비속어'를 포함할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = {"비속어", "욕설"})
        void error2(String profanity) {
            assertThatExceptionOfType(ProfanityProductNameException.class)
                    .isThrownBy(() -> createProductName(profanity));
        }
    }
}
