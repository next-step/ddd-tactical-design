package kitchenpos.menus.tobe.menugroup.domain.model;

import static org.assertj.core.api.Assertions.assertThat;

import kitchenpos.common.infra.Profanities;
import kitchenpos.common.tobe.FakeProfanities;
import kitchenpos.common.tobe.domain.DisplayedName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupV2Test {

    private final Profanities profanities = new FakeProfanities();

    @DisplayName("메뉴그룹을 생성할 수 있다")
    @Test
    void menuGroup() {
        final MenuGroupV2 menuGroup = new MenuGroupV2(new DisplayedName("그룹1", profanities));
        assertThat(menuGroup).isNotNull();
    }
}
