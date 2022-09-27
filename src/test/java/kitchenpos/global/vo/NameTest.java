package kitchenpos.global.vo;

import static kitchenpos.global.TobeFixtures.name;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import kitchenpos.global.exception.EmptyNameException;
import kitchenpos.global.exception.ProfanityNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class NameTest {

    @DisplayName("이름을 생성할 수 있다.")
    @Test
    void create() {
        Name actual = name("gmoon");

        assertThat(actual).isEqualTo(name("gmoon"));
        assertThat(actual.hashCode() == name("gmoon").hashCode())
                .isTrue();
    }

    @DisplayName("이름 예외 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("공백을 포함할 수 없다")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullAndEmptySource
        void error1(String actual) {
            assertThatExceptionOfType(EmptyNameException.class)
                    .isThrownBy(() -> name(actual));
        }

        @DisplayName("'비속어'를 포함할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = {"비속어", "욕설"})
        void error2(String profanityWord) {
            assertThatExceptionOfType(ProfanityNameException.class)
                    .isThrownBy(() -> name(profanityWord));
        }
    }
}
