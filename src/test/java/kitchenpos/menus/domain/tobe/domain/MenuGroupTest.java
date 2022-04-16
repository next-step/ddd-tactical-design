package kitchenpos.menus.domain.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class MenuGroupTest {

    @DisplayName("메뉴 그룹 이름으로 메뉴 그룹을 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new MenuGroup("요리 세트"))
                .doesNotThrowAnyException();
    }
}
