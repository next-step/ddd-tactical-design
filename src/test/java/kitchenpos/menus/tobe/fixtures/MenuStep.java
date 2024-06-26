package kitchenpos.menus.tobe.fixtures;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.Name;

import java.util.UUID;

public class MenuStep {

    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;

    public MenuStep(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroup 메뉴_그룹_생성(UUID menuGroupId, Name name) {
        return menuGroupRepository.save(new MenuGroup(menuGroupId, name));
    }

    public MenuGroup 메뉴_그룹_생성() {
        return 메뉴_그룹_생성(UUID.randomUUID(), new Name("메뉴 그룹"));
    }

}
