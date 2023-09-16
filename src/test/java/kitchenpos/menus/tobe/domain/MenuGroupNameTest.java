package kitchenpos.menus.tobe.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {
    @DisplayName("메뉴그룹의 이름은 비어있을 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> new MenuGroupName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
