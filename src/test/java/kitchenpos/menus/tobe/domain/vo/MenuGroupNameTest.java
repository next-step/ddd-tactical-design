package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.exception.IllegalMenuGroupNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuGroupNameTest {


    @DisplayName("메뉴그룹(MenuGroup)은 반드시 이름을 가진다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(String name) {
        assertThatThrownBy(() -> new MenuGroupName(name))
                .isInstanceOf(IllegalMenuGroupNameException.class);
    }

}
