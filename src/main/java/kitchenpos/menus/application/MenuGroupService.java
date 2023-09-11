package kitchenpos.menus.application;

import kitchenpos.menus.dto.MenuGroupCreateRequest;
import kitchenpos.menus.dto.MenuGroupDetailResponse;
import kitchenpos.menus.mapper.MenuMapper;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static kitchenpos.menus.mapper.MenuMapper.toMenuGroupDetailResponse;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupDetailResponse create(final MenuGroupCreateRequest request) {
        final MenuGroup menuGroup = MenuGroup.create(MenuGroupName.create(request.getName()));
        return toMenuGroupDetailResponse(menuGroupRepository.save(menuGroup));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupDetailResponse> findAll() {
        return menuGroupRepository.findAll()
                .stream()
                .map(MenuMapper::toMenuGroupDetailResponse)
                .collect(Collectors.toUnmodifiableList());
    }

}
