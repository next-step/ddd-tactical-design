package kitchenpos.menus.application;


import kitchenpos.menus.application.dto.MenuGroupCreateRequest;
import kitchenpos.menus.application.dto.MenuGroupInfoResponse;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupInfoResponse create(final MenuGroupCreateRequest request) {
        MenuGroup menuGroup = MenuGroup.create(UUID.randomUUID(), request.getName());
        MenuGroup savedMenuGroup = menuGroupRepository.save(menuGroup);
        return new MenuGroupInfoResponse(savedMenuGroup.getId(), savedMenuGroup.getName());
    }

    @Transactional(readOnly = true)
    public List<MenuGroupInfoResponse> findAll() {
        List<MenuGroup> savedMenuGroupList = menuGroupRepository.findAll();
        return savedMenuGroupList.stream()
                .map(a -> new MenuGroupInfoResponse(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }
}
