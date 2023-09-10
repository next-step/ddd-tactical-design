package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.products.exception.ProductDisplayedNameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("메뉴 그룹 이름")
class MenuGroupDisplayedNameTest {

    @DisplayName("[성공] 이름을 생성한다.")
    @Test
    void create1() {
        assertThatNoException().isThrownBy(
                () -> new MenuGroupDisplayedName("표준어"));
    }


    @DisplayName("[실패] 이름은 null이거나 공백일 수 없다.")
    @ParameterizedTest
    @NullAndEmptySource
    void create_test_3(String name) {
        assertThatThrownBy(
                () -> new MenuGroupDisplayedName(name))
                .isInstanceOf(ProductDisplayedNameException.class)
                .hasMessage(MenuErrorCode.NAME_IS_NULL_OR_EMPTY.getMessage());
    }
}
