package kitchenpos.menus.domain.tobe;

import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class MenuNameTest {
    @DisplayName("Menu는 MenuName 항상 가지고 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"반반치킨", "통구이"})
    void name(String name) {
        final MenuName menuName = new MenuName(name, new StubBanWordFilter(false));

        assertThat(menuName).extracting("name").isEqualTo(name);
    }

    @DisplayName("MenuName에는 ban word가 포함될 수 없다")
    @ParameterizedTest
    @ValueSource(strings = {"비속어", "욕설"})
    void ban_words(String name) {

        assertThatCode(() -> new MenuName(name, new StubBanWordFilter(true)));
    }
}
