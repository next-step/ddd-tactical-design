package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DisplayedTest {

    @DisplayName("메뉴의 노출/숨김을 생성할 수 있다.")
    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void 생성(final boolean displayed) {
        assertDoesNotThrow(
            () -> new Displayed(displayed)
        );
    }

    @DisplayName("메뉴의 노출/숨김 노출 상태를 확인할 수 있다.")
    @Test
    void 노출상태() {
        assertAll(
            () -> assertThat(new Displayed(true).isDisplayed()).isTrue(),
            () -> assertThat(new Displayed(true).isHidden()).isFalse()
        );
    }

    @DisplayName("메뉴의 노출/숨김 숨김 상태를 확인할 수 있다.")
    @Test
    void 숨김상태() {
        assertAll(
            () -> assertThat(new Displayed(false).isDisplayed()).isFalse(),
            () -> assertThat(new Displayed(false).isHidden()).isTrue()
        );
    }

    @DisplayName("메뉴 노출/숨김 간 동등성을 확인할 수 있다.")
    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void 동등성(final boolean displayed) {
        final Displayed displayed1 = new Displayed(displayed);
        final Displayed displayed2 = new Displayed(displayed);

        assertThat(displayed1).isEqualTo(displayed2);
    }
}
