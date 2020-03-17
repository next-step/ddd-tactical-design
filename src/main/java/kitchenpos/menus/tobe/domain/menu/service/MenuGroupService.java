package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.menu.repository.MenuGroupRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroup create(final MenuGroup menuGroup) {
        return menuGroupRepository.save(menuGroup);
    }

    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }

    public boolean existsMenuGroup(Long id) {
        return menuGroupRepository.existsById(id);
    }
}
