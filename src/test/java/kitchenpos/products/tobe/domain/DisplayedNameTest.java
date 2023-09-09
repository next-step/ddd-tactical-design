package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakeProfanityPolicy;
import kitchenpos.products.exception.DisplayedNameException;
import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.tobe.domain.policy.ProfanityPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("이름")
class DisplayedNameTest {

    private ProfanityPolicy profanityPolicy;

    @BeforeEach
    void setUp() {
        profanityPolicy = new FakeProfanityPolicy();
    }

    @DisplayName("[성공] 이름을 생성한다.")
    @Test
    void create1() {
        assertThatNoException().isThrownBy(
                () -> new DisplayedName("표준어", profanityPolicy));
    }

    @DisplayName("[실패] 이름에 비속어가 포함될 수 없다.")
    @Test
    void create2() {
        assertThatThrownBy(
                () -> new DisplayedName("비속어", profanityPolicy))
                .isInstanceOf(DisplayedNameException.class)
                .hasMessage(ProductErrorCode.NAME_HAS_PROFANITY.getMessage());
    }

    @DisplayName("[실패] 이름은 null이거나 공백일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create_test_3(String name) {
        assertThatThrownBy(
                () -> new DisplayedName(name, profanityPolicy))
                .isInstanceOf(DisplayedNameException.class)
                .hasMessage(ProductErrorCode.NAME_IS_NULL_OR_EMPTY.getMessage());
    }
}
