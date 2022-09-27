package kitchenpos.menus.tobe.domain.vo;

import static kitchenpos.menus.tobe.domain.MenuFixtures.menuName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import kitchenpos.menus.tobe.domain.exception.EmptyMenuNameException;
import kitchenpos.menus.tobe.domain.exception.ProfanityMenuNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuNameTest {

    @DisplayName("상품의 이름을 생성할 수 있다.")
    @Test
    void create() {
        MenuName actual = menuName("gmoon");

        assertThat(actual).isEqualTo(menuName("gmoon"));
        assertThat(actual.hashCode() == menuName("gmoon").hashCode())
                .isTrue();
    }

    @DisplayName("메뉴 이름 예외 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("공백을 포함할 수 없다")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullAndEmptySource
        void error1(String name) {
            assertThatExceptionOfType(EmptyMenuNameException.class)
                    .isThrownBy(() -> menuName(name));
        }

        @DisplayName("'비속어'를 포함할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = {"비속어", "욕설"})
        void error2(String profanity) {
            assertThatExceptionOfType(ProfanityMenuNameException.class)
                    .isThrownBy(() -> menuName(profanity));
        }
    }
}
