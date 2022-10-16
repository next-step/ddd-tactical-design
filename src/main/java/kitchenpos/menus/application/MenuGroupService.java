package kitchenpos.menus.application;

import kitchenpos.menus.model.MenuGroupModel;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuGroupName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupModel create(final String name) {
        MenuGroupName menuGroupName = new MenuGroupName(name);
        final MenuGroup menuGroup = new MenuGroup(menuGroupName);
        MenuGroup saveMenuGroup = menuGroupRepository.save(menuGroup);
        return new MenuGroupModel(saveMenuGroup.id(), saveMenuGroup.nameValue());
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
