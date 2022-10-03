package kitchenpos.menus.application;

import java.util.List;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupName;
import kitchenpos.menus.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final MenuGroup request) {
        MenuGroupName menuGroupName = new MenuGroupName(request.getNameValue());
        MenuGroup menuGroup = new MenuGroup(menuGroupName);
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
