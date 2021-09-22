package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.FakeProfanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupTest {

    private final Profanities profanities = new FakeProfanities();

    @DisplayName("메뉴그룹을 생성할 수 있다")
    @Test
    void menuGroup() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("그룹1", profanities));
    }
}
