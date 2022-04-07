package kitchenpos.menus.domain.tobe;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuDisplayedTest {

    @DisplayName("메뉴 숨김여부를 생성할 수 있다")
    @Test
    void create() {
        final MenuDisplayed menuDisplayed = new MenuDisplayed(true);

        assertThat(menuDisplayed).extracting("displayed").isEqualTo(true);
    }

    @DisplayName("메뉴 보여짐 상태로 변경할 수 있다")
    @Test
    void show() {
        final MenuDisplayed menuDisplayed = new MenuDisplayed(false);

        menuDisplayed.show();

        assertThat(menuDisplayed).extracting("displayed").isEqualTo(true);
    }

    @DisplayName("메뉴 숨김 상태로 변경할 수 있다")
    @Test
    void hide() {
        final MenuDisplayed menuDisplayed = new MenuDisplayed(true);

        menuDisplayed.hide();

        assertThat(menuDisplayed).extracting("displayed").isEqualTo(false);
    }
}
