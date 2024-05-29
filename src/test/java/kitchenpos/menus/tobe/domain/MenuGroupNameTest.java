package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.exception.InvalidMenuGroupNameException;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴그룹 이름")
class MenuGroupNameTest {

    private static final String 메뉴그룹_이름 = "메뉴그룹 이름";

    @DisplayName("[성공] 메뉴그룹 이름이 생성된다.")
    @Test
    void create() {
        MenuGroupName menuGroupName = MenuGroupName.from(메뉴그룹_이름);

        assertThat(menuGroupName).isEqualTo(new MenuGroupName(메뉴그룹_이름));
    }

    @DisplayName("[실패] 메뉴그룹 이름은 비어둘 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void fail(String name) {
        assertThatThrownBy(() -> MenuGroupName.from(name))
                .isInstanceOf(InvalidMenuGroupNameException.class);
    }
}
