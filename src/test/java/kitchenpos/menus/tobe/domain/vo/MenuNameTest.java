package kitchenpos.menus.tobe.domain.vo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import kitchenpos.menus.tobe.domain.exception.EmptyMenuNameException;
import kitchenpos.menus.tobe.domain.exception.ProfanityMenuNameException;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuNameTest {

    private PurgomalumClient profanity;

    @BeforeEach
    void setUp() {
        profanity = new FakePurgomalumClient();
    }

    @DisplayName("상품의 이름을 생성할 수 있다.")
    @Test
    void create() {
        MenuName actual = createMenuName("gmoon");

        assertThat(actual).isEqualTo(createMenuName("gmoon"));
        assertThat(actual.hashCode() == createMenuName("gmoon").hashCode())
                .isTrue();
    }

    private MenuName createMenuName(String name) {
        return new MenuName(name, profanity);
    }

    @DisplayName("메뉴 이름 예외 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("공백을 포함할 수 없다")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @NullAndEmptySource
        void error1(String name) {
            assertThatExceptionOfType(EmptyMenuNameException.class)
                    .isThrownBy(() -> createMenuName(name));
        }

        @DisplayName("'비속어'를 포함할 수 없다.")
        @ParameterizedTest(name = "{displayName}[{index}] - {arguments}")
        @ValueSource(strings = {"비속어", "욕설"})
        void error2(String profanity) {
            assertThatExceptionOfType(ProfanityMenuNameException.class)
                    .isThrownBy(() -> createMenuName(profanity));
        }
    }
}
