package kitchenpos.apply.menus.tobe.application;

import kitchenpos.apply.menus.tobe.domain.MenuGroup;
import kitchenpos.apply.menus.tobe.ui.MenuGroupRequest;
import kitchenpos.apply.menus.tobe.ui.MenuGroupResponse;
import kitchenpos.apply.menus.tobe.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupResponse create(final MenuGroupRequest request) {
        MenuGroup menuGroup = menuGroupRepository.save(MenuGroup.of(request.getName()));
        return new MenuGroupResponse(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll().stream()
                .map(MenuGroupResponse::new)
                .collect(Collectors.toList());
    }
}
