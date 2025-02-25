package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupId;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
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
    public MenuGroup create(final MenuGroup request) {
        MenuGroup menuGroup = new MenuGroup(
                MenuGroupId.generate(),
                request.getName()
        );

        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
