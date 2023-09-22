package kitchenpos.menus.application;


import kitchenpos.menus.domain.MenuGroupRepository;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupName;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroup request) {
        MenuGroup menuGroup = MenuGroup.of(new MenuGroupName(request.getName()));
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
