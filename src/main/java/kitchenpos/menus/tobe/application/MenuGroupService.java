package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.dto.CreateMenuGroupRequest;
import kitchenpos.menus.tobe.dto.MenuGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("TobeMenuGroupService")
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupResponse create(final CreateMenuGroupRequest request) {
        return MenuGroupResponse.from(menuGroupRepository.save(request.toMenuGroup()));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return menuGroupRepository.findAll()
                .stream().map(MenuGroupResponse::from)
                .collect(Collectors.toList());
    }
}
