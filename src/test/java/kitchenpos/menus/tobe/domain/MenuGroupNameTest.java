package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("메뉴 그룹명")
public class MenuGroupNameTest {

    @DisplayName("메뉴 그룹명 생성")
    @Test
    public void createMenuGroupName() {
        assertDoesNotThrow(() -> new MenuGroupName("메뉴 그룹 이름"));
    }

    @DisplayName("상품 이름은 null 또는 공백일 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createEmptyName(String name) {
        assertThatThrownBy(() -> new MenuGroupName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("상품명은 null 이나 공백일 수 없습니다.");
    }

}
