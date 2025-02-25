package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuGroupNameTest {

    @DisplayName("메뉴그룹명에 null 또는 공백이 입력되면 예외가 발생한다")
    @NullAndEmptySource
    @ParameterizedTest
    void validate(String name) {
        assertThatThrownBy(() -> new MenuGroupName(name))
                .isInstanceOf(InvalidMenuGroupNameException.class);
    }
}
