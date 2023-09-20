package kitchenpos.menus.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.UUID;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupName;
import org.junit.jupiter.api.Test;

class MenuGroupServiceTest {

    private final MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();
    @Test
    public void create() throws Exception {
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), new MenuGroupName("메뉴그룹"));
        MenuGroup saveMenuGroup = menuGroupRepository.save(menuGroup);
        assertThat(menuGroup).isEqualTo(new MenuGroup(saveMenuGroup.getId(), new MenuGroupName("메뉴그룹")));
    }

}