package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.vo.MenuGroupName;
import kitchenpos.menus.ui.dto.CreateMenuGroupRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateMenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public CreateMenuGroupService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroup create(final CreateMenuGroupRequest request) {
        MenuGroupName menuGroupName = new MenuGroupName(request.name);
        final MenuGroup menuGroup = new MenuGroup(menuGroupName);
        return menuGroupRepository.save(menuGroup);
    }
}
