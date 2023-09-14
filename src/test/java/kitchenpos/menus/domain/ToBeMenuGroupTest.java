package kitchenpos.menus.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import kitchenpos.menus.domain.tobe.domain.ToBeMenuGroup;

public class ToBeMenuGroupTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("메뉴글부 이름이 null 이면 등록 불가")
    void menuGroup(String name) {
        assertThatThrownBy(() -> new ToBeMenuGroup(name))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("메뉴 그룹 이름이 입력되야 합니다.");
    }
}
