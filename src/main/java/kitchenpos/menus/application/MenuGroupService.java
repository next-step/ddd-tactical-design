package kitchenpos.menus.application;


import kitchenpos.menus.application.dto.MenuGroupCreateRequest;
import kitchenpos.menus.application.dto.MenuGroupInfoResponse;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.NewMenuGroup;
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
        NewMenuGroup newMenuGroup = NewMenuGroup.create(UUID.randomUUID(), request.getName());
        NewMenuGroup savedMenuGroup = menuGroupRepository.save(newMenuGroup);
        return new MenuGroupInfoResponse(savedMenuGroup.getId(), savedMenuGroup.getName());
    }

    @Transactional(readOnly = true)
    public List<MenuGroupInfoResponse> findAll() {
        List<NewMenuGroup> savedMenuGroupList = menuGroupRepository.findAll();
        return savedMenuGroupList.stream()
                .map(a -> new MenuGroupInfoResponse(a.getId(), a.getName()))
                .collect(Collectors.toList());
    }
}
