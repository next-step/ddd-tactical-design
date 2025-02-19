package kitchenpos.menu.application;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuGroupName;
import kitchenpos.menu.domain.model.MenuGroupNameCreationService;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;
    private final MenuGroupNameCreationService menuGroupNameCreationService;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository, MenuGroupNameCreationService menuGroupNameCreationService) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuGroupNameCreationService = menuGroupNameCreationService;
    }

    @Transactional
    public MenuGroup create(final MenuGroup request) {
        final String name = request.getName();
        MenuGroupName menuGroupName = menuGroupNameCreationService.createName(name);
        final MenuGroup menuGroup = new MenuGroup(menuGroupName, UUID.randomUUID());
        return menuGroupRepository.save(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroup> findAll() {
        return menuGroupRepository.findAll();
    }
}
