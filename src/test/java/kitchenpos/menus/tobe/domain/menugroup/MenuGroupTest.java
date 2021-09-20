package kitchenpos.menus.tobe.domain.menugroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;
import kitchenpos.ToBeFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuGroupTest {

    @DisplayName("메뉴 그룹을 생성할 수 있다.")
    @Test
    void 생성() {
        assertDoesNotThrow(
            () -> ToBeFixtures.menuGroup("두마리메뉴")
        );
    }

    @DisplayName("메뉴 그룹 간 동등성을 확인할 수 있다.")
    @Test
    void 동등성() {
        final UUID id = UUID.randomUUID();

        final MenuGroup menuGroup1 = ToBeFixtures.menuGroup(id, "두마리메뉴");
        final MenuGroup menuGroup2 = ToBeFixtures.menuGroup(id, "두마리메뉴");

        assertThat(menuGroup1).isEqualTo(menuGroup2);
    }
}
