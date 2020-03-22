package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    private final MenuGroup menuGroup = new MenuGroup(1L, "추천 메뉴");

    @DisplayName("메뉴 객체를 생성한다.")
    @Test
    void create() {
        assertThatCode(() -> new Menu("순대국", BigDecimal.valueOf(7_000L), menuGroup)).doesNotThrowAnyException();
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "-1000")
    void canNotCreate(final BigDecimal price) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Menu("순대국", price, menuGroup));
    }

    @DisplayName("메뉴의 이름이 책정되지 않으면 생성할 수 없다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = "")
    void canNotCreate(final String name) {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Menu(name, BigDecimal.valueOf(7_000L), menuGroup));
    }
}
