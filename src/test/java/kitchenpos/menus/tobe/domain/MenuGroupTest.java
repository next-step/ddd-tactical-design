package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuGroupTest {

    @DisplayName("메뉴 그룹 객체를 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new MenuGroup("추천 메뉴")).doesNotThrowAnyException();
    }

    @DisplayName("메뉴 그룹의 이름이 책정되지 않으면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void canNotCreate(final String name) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new MenuGroup(name));
    }
}
