package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuGroupCreateRequest;
import kitchenpos.menus.dto.MenuGroupResponse;
import kitchenpos.menus.mapper.MenuGroupMapper;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;
    private final MenuGroupMapper menuGroupMapper;

    public MenuGroupService(
            final MenuGroupRepository menuGroupRepository,
            final MenuGroupMapper menuGroupMapper
    ) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuGroupMapper = menuGroupMapper;
    }

    @Transactional
    public MenuGroupResponse create(final MenuGroupCreateRequest request) {
        MenuGroup menuGroup = menuGroupRepository.save(MenuGroup.createMenuGroup(request.getName()));
        return menuGroupMapper.toMenuGroupResponse(menuGroup);
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll().stream().map(v -> menuGroupMapper.toMenuGroupResponse(v))
                .collect(Collectors.toList());
    }
}
