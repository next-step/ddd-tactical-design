package kitchenpos.menu.tobe.domain.menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MenuDisplayStatusTest {

    @Test
    @DisplayName("MenuDisplayStatus 객체를 생성할 수 있다")
    void create() {
        // when
        MenuDisplayStatus displayed = MenuDisplayStatus.of(true);
        MenuDisplayStatus hidden = MenuDisplayStatus.of(false);

        // then
        assertThat(displayed.isDisplayed()).isTrue();
        assertThat(hidden.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("MenuDisplayStatus를 표시 상태로 변경할 수 있다")
    void show() {
        // given
        MenuDisplayStatus status = MenuDisplayStatus.of(false);

        // when
        status.show();

        // then
        assertThat(status.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("MenuDisplayStatus를 숨김 상태로 변경할 수 있다")
    void hide() {
        // given
        MenuDisplayStatus status = MenuDisplayStatus.of(true);

        // when
        status.hide();

        // then
        assertThat(status.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("MenuDisplayStatus 객체끼리 동등성 비교가 가능하다")
    void equals() {
        // given
        MenuDisplayStatus status1 = MenuDisplayStatus.of(true);
        MenuDisplayStatus status2 = MenuDisplayStatus.of(true);
        MenuDisplayStatus status3 = MenuDisplayStatus.of(false);

        // then
        assertThat(status1).isEqualTo(status2);
        assertThat(status1).isNotEqualTo(status3);
    }
}