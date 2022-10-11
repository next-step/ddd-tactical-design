package kitchenpos.menus.domain;

import static kitchenpos.menus.MenuFixtures.menuGroup;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupTest {

    @DisplayName("UUID가 일치하면 동일한 메뉴그룹으로 취급한다.")
    @Test
    void sameUUID() {
        // given
        UUID id = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        MenuGroup 치킨그룹 = menuGroup(
            id,
            "사과"
        );
        MenuGroup 햄버거그룹 = menuGroup(
            id,
            "바나나"
        );

        // when
        boolean result = 치킨그룹.equals(햄버거그룹);

        // then
        assertThat(result).isTrue();
    }
}
