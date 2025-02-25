package kitchenpos.menu.application.facade;

import java.util.List;
import kitchenpos.menu.application.dto.MenuGroupRequest;
import kitchenpos.menu.application.dto.MenuGroupResponse;
import kitchenpos.menu.domain.service.MenuGroupCommandService;
import kitchenpos.menu.domain.service.MenuGroupQueryService;
import org.springframework.stereotype.Component;

@Component
public class MenuGroupFacade {

    private final MenuGroupQueryService menuGroupQueryService;
    private final MenuGroupCommandService menuGroupCommandService;

    public MenuGroupFacade(
        MenuGroupQueryService menuGroupQueryService,
        MenuGroupCommandService menuGroupCommandService
    ) {
        this.menuGroupQueryService = menuGroupQueryService;
        this.menuGroupCommandService = menuGroupCommandService;
    }

    public MenuGroupResponse.GetGroup create(MenuGroupRequest.Create request) {
        return MenuGroupResponse.GetGroup.fromVo(menuGroupCommandService.create(request.toVo()));
    }

    public List<MenuGroupResponse.GetGroup> findAll() {
        return menuGroupQueryService.findAll()
           .stream()
           .map(MenuGroupResponse.GetGroup::fromVo)
           .toList();
    }
}
