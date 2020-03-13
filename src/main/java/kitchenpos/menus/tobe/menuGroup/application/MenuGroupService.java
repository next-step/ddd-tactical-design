package kitchenpos.menus.tobe.menuGroup.application;

import kitchenpos.menus.tobe.menuGroup.domain.MenuGroup;
import kitchenpos.menus.tobe.menuGroup.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MenuGroupService {

    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public Long create(final String name) {
        final MenuGroup newMenuGroup = new MenuGroup(name);
        return menuGroupRepository.save(newMenuGroup).getId();
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }
}
