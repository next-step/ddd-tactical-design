package kitchenpos.menu.tobe.application;

import kitchenpos.menu.tobe.application.dto.CreateMenuGroupRequest;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service("newMenuGroupService")
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final CreateMenuGroupRequest request) {
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), request.name());

        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
