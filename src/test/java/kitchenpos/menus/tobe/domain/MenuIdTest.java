package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuIdTest {

    @DisplayName("메뉴 key에 null이 입력되면 예외가 발생한다")
    @NullSource
    @ParameterizedTest
    void notNull(UUID id) {
        assertThatThrownBy(() -> new MenuId(id))
                .isInstanceOf(NullPointerException.class);
    }
}
