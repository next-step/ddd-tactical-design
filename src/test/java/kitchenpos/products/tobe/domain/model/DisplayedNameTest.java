package kitchenpos.products.tobe.domain.model;

import static kitchenpos.products.tobe.exception.WrongDisplayedNameExeption.DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY;
import static kitchenpos.products.tobe.exception.WrongDisplayedNameExeption.DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.exception.WrongDisplayedNameExeption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class DisplayedNameTest {

    @DisplayName("빈 이름으로 DisplayedName을 생성할 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void wrongName(final String name) {
        assertThatThrownBy(
            () -> new DisplayedName(name)
        ).isInstanceOf(WrongDisplayedNameExeption.class)
            .hasMessage(DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY);
    }

    @DisplayName("올바른 이름으로 DisplayedName을 생성할 수 있다")
    @ParameterizedTest
    @ValueSource(strings = {"상품1", "상품2", "상품3"})
    void name(final String name) {
        final DisplayedName displayedName = new DisplayedName(name);

        assertThat(displayedName).isNotNull();
    }

    @DisplayName("이름에 비속어가 포함되어 있으면 validateProfanity를 실행시 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설 포함"})
    void profanityName(final String wrongName) {
        final DisplayedName displayedName = new DisplayedName(wrongName);

        assertThatThrownBy(
            () -> displayedName.validateProfanity(new FakePurgomalumClient())
        ).isInstanceOf(WrongDisplayedNameExeption.class)
            .hasMessage(DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY);
    }

    @DisplayName("name이 같으면 equals의 결과도 같다")
    @ParameterizedTest
    @ValueSource(strings = {"상품1", "상품2", "상품3"})
    void equals(final String name) {
        final DisplayedName displayedName1 = new DisplayedName(name);
        final DisplayedName displayedName2 = new DisplayedName(name);

        assertThat(displayedName1.equals(displayedName2)).isTrue();
    }

}
