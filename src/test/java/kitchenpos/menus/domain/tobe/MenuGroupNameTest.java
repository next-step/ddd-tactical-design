package kitchenpos.menus.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupNameTest {

    @Test
    @DisplayName("메뉴 그룹 이름 생성이 가능하다")
    void constructor() {
        final MenuGroupName expected = new MenuGroupName("메뉴 그룹 이름");
        assertThat(expected).isNotNull();
    }

    @ParameterizedTest
    @DisplayName("메뉴 그룹의 이름은 필수이다")
    @NullAndEmptySource
    void constructor_with_null_and_empty_value(final String value) {
        assertThatThrownBy(() -> new MenuGroupName(value))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
