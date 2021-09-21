package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupNameTest {

    @DisplayName("메뉴그룹의 이름을 설정한다.")
    @Test
    void create() {
        final String name = "치킨";
        final MenuGroupName actual = new MenuGroupName(name);

        assertThat(actual.getName()).isEqualTo(name);
    }

    @DisplayName("메뉴그룹의 이름은 필수 값이다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> new MenuGroupName(name))
            .isInstanceOf(IllegalArgumentException.class);
    }

}
