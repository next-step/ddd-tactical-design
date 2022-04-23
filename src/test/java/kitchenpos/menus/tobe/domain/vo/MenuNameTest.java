package kitchenpos.menus.tobe.domain.vo;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.menus.tobe.domain.FakeProfanities;

class MenuNameTest {

    @ParameterizedTest
    @ValueSource(strings = { "비속어", "욕설" })
    void 메뉴의_이름은_비속어가_포함될수_없다(final String displayedName) {
        //given
        final Profanities profanities = new FakeProfanities();
        //when
        //then
        assertThatThrownBy(() -> new DisplayedName(displayedName, profanities)).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void 메뉴의_이름은_비어있을수_없다(final String displayedName) {
        //given
        final Profanities profanities = new FakeProfanities();
        //when
        //then
        assertThatThrownBy(() -> new DisplayedName(displayedName, profanities)).isInstanceOf(IllegalArgumentException.class);
    }
}
