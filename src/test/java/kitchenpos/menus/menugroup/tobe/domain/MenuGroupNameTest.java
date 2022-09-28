package kitchenpos.menus.menugroup.tobe.domain;

import kitchenpos.menus.menugroup.tobe.domain.exception.InvalidMenuGroupNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {

    @DisplayName("메뉴그룹명을 생성한다.")
    @Test
    void valueOf() {
        final MenuGroupName menuGroupName = MenuGroupName.valueOf("인기상품");

        assertThat(menuGroupName.value()).isEqualTo("인기상품");
    }

    @ParameterizedTest(name = "상품명에는 비어있을 수 없습니다. name={0}")
    @NullAndEmptySource
    void invalid_name(final String name) {
        assertThatThrownBy(() -> MenuGroupName.valueOf(name))
                .isInstanceOf(InvalidMenuGroupNameException.class);
    }
}
