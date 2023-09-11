package kitchenpos.menus.application;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.dto.MenuGroupCreateRequest;
import kitchenpos.menus.dto.MenuGroupDetailResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupDetailResponse create(final MenuGroupCreateRequest request) {
        final String name = request.getName();
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(UUID.randomUUID());
        menuGroup.setName(name);
        return toMenuGroupDetailResponse(menuGroupRepository.save(menuGroup));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupDetailResponse> findAll() {
        return menuGroupRepository.findAll()
                .stream()
                .map(this::toMenuGroupDetailResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    private MenuGroupDetailResponse toMenuGroupDetailResponse(MenuGroup menuGroup) {
        return new MenuGroupDetailResponse(menuGroup.getId(), menuGroup.getName());
    }
}
