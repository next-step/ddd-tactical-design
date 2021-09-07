package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class MenuGroupNameTest {
    @DisplayName("메뉴 그룹 이름은 비어있을 수 없다.")
    @Test
    void emptyName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuGroupName(null))
                .withMessage("메뉴 그룹 이름은 필수값입니다.");
    }

    @DisplayName("적절하지 않은 메뉴 그룹 이름은 validation 에 실패한다.")
    @Test
    void validateName() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MenuGroupName("메뉴 그룹 이름").validate(name -> true))
                .withMessage("적절하지 않은 메뉴 그룹 이름입니다.");
    }
}
