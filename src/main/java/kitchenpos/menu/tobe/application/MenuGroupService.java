package kitchenpos.menu.tobe.application;

import kitchenpos.menu.tobe.application.dto.CreateMenuGroupRequest;
import kitchenpos.menu.tobe.application.dto.MenuGroupResponse;
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
    public MenuGroupResponse create(final CreateMenuGroupRequest request) {
        MenuGroup menuGroup = new MenuGroup(UUID.randomUUID(), request.name());

        MenuGroup savedMenuGroup = menuGroupRepository.save(menuGroup);

        return new MenuGroupResponse(savedMenuGroup.getId(), savedMenuGroup.getName());
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll().stream()
                .map(menuGroup -> new MenuGroupResponse(menuGroup.getId(), menuGroup.getName()))
                .toList();
    }

}
