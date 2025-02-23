package kitchenpos.menu.application.facade;

import java.util.List;
import kitchenpos.menu.application.dto.MenuGroupRequest;
import kitchenpos.menu.application.dto.MenuGroupResponse;
import kitchenpos.menu.domain.service.MenuGroupService;
import org.springframework.stereotype.Component;

@Component
public class MenuGroupFacade {

    private final MenuGroupService menuGroupService;

    public MenuGroupFacade(
        final MenuGroupService menuGroupService
    ) {
        this.menuGroupService = menuGroupService;
    }

    public MenuGroupResponse.GetGroup create(MenuGroupRequest.Create request) {
        return MenuGroupResponse.GetGroup.fromVo(menuGroupService.create(request.toVo()));
    }

    public List<MenuGroupResponse.GetGroup> findAll() {
        return menuGroupService.findAll()
           .stream()
           .map(MenuGroupResponse.GetGroup::fromVo)
           .toList();
    }
}
