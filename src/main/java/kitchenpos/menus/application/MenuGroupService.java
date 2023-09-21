package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.request.MenuGroupCreateRequest;
import kitchenpos.menus.application.dto.response.MenuGroupCreateResponse;
import kitchenpos.menus.application.dto.response.MenuGroupResponse;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
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
    public MenuGroupCreateResponse create(final MenuGroupCreateRequest request) {
        final MenuGroup menuGroup = new MenuGroup(request.getName());
        return MenuGroupCreateResponse.of(menuGroupRepository.save(menuGroup));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll().stream()
                .map(MenuGroupResponse::of)
                .collect(Collectors.toList());
    }
}
