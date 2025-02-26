package kitchenpos.menu.application;

import java.util.List;
import kitchenpos.menu.application.dto.CreateMenuGroupServiceRq;
import kitchenpos.menu.application.dto.MenuGroupServiceRs;
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

    public MenuGroupService(final MenuGroupRepository menuGroupRepository,
                            MenuGroupNameCreationService menuGroupNameCreationService) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuGroupNameCreationService = menuGroupNameCreationService;
    }

    @Transactional
    public MenuGroupServiceRs create(final CreateMenuGroupServiceRq request) {
        final String name = request.getName();
        MenuGroupName menuGroupName = menuGroupNameCreationService.createName(name);
        MenuGroup menuGroup = menuGroupRepository.save(new MenuGroup(menuGroupName));
        return new MenuGroupServiceRs(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupServiceRs> findAll() {
        return menuGroupRepository.findAll().stream()
                .map(MenuGroupServiceRs::new)
                .toList();
    }
}
