package kitchenpos.menus.application;

import java.util.List;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupName;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.ui.request.MenuGroupCreateRequest;
import kitchenpos.menus.ui.response.MenuGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Transactional
    public MenuGroupResponse create(MenuGroupCreateRequest request) {
        MenuGroupName menuGroupName = new MenuGroupName(request.getName());
        MenuGroup menuGroup = new MenuGroup(menuGroupName);
        return MenuGroupResponse.from(menuGroupRepository.save(menuGroup));
    }

    @Transactional(readOnly = true)
    public List<MenuGroupResponse> findAll() {
        return MenuGroupResponse.of(menuGroupRepository.findAll());
    }
}
