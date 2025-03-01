package kitchenpos.menu.application.facade;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.application.dto.MenuRequest;
import kitchenpos.menu.application.dto.MenuResponse;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.service.MenuCommandService;
import kitchenpos.menu.domain.service.MenuQueryService;
import org.springframework.stereotype.Component;

@Component
public class MenuFacade {

    private final MenuQueryService menuQueryService;
    private final MenuCommandService menuCommandService;

    public MenuFacade(
        MenuQueryService menuQueryService,
        MenuCommandService menuCommandService
    ) {
        this.menuQueryService = menuQueryService;
        this.menuCommandService = menuCommandService;
    }

    public MenuResponse.GetMenu create(MenuRequest.Create request) {
        return MenuResponse.GetMenu.fromVo(menuCommandService.create(request.toVo()));
    }

    public MenuResponse.GetMenu changePrice(MenuRequest.UpdatePrice request) {
        return MenuResponse.GetMenu.fromVo(menuCommandService.changePrice(request.toVo()));
    }

    public MenuResponse.GetMenu display(UUID menuId) {
        return MenuResponse.GetMenu.fromVo(menuCommandService.display(MenuId.of(menuId)));
    }

    public MenuResponse.GetMenu hide(UUID menuId) {
        var result = menuCommandService.hide(MenuId.of(menuId));
        return MenuResponse.GetMenu.fromVo(result);
    }

    public List<MenuResponse.GetMenu> findAll() {
        return menuQueryService.findAll()
            .stream()
            .map(MenuResponse.GetMenu::fromVo)
            .toList();
    }
}
