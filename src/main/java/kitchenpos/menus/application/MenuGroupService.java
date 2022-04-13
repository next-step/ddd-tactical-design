package kitchenpos.menus.application;

import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.ui.dto.MenuGroupRequest;
import kitchenpos.menus.ui.dto.MenuGroupResponse;
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
        return new MenuGroupResponse(menuGroupRepository.save(request.toEntity()));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        List<MenuGroup> menuGroups = menuGroupRepository.findAll();
        return menuGroups.stream()
                .map(menuGroup -> new MenuGroupResponse(menuGroup))
                .collect(Collectors.toList());
    }
}
