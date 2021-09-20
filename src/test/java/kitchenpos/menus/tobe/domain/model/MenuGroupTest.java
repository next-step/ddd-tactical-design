package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.tobe.domain.DisplayedName;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @DisplayName("메뉴그룹을 생성할 수 있다")
    @Test
    void menuGroup() {
        final MenuGroup menuGroup = new MenuGroup(new DisplayedName("그룹1", purgomalumClient));
    }
}
