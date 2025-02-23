package kitchenpos.menu.application.facade;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.application.dto.MenuRequest;
import kitchenpos.menu.application.dto.MenuResponse;
import kitchenpos.menu.domain.service.MenuService;
import org.springframework.stereotype.Component;

@Component
public class MenuFacade {

    private final MenuService menuService;

    public MenuFacade(
        MenuService menuService
    ) {
        this.menuService = menuService;
    }

    public MenuResponse.GetMenu create(MenuRequest.Create request) {
        return MenuResponse.GetMenu.fromVo(menuService.create(request.toVo()));
    }

    public MenuResponse.GetMenu changePrice(MenuRequest.UpdatePrice request) {
        return MenuResponse.GetMenu.fromVo(menuService.changePrice(request.toVo()));
    }

    public MenuResponse.GetMenu display(UUID menuId) {
        return MenuResponse.GetMenu.fromVo(menuService.display(menuId));
    }

    public MenuResponse.GetMenu hide(UUID menuId) {
        return MenuResponse.GetMenu.fromVo(menuService.hide(menuId));
    }

    public List<MenuResponse.GetMenu> findAll() {
        return menuService.findAll()
            .stream()
            .map(MenuResponse.GetMenu::fromVo)
            .toList();
    }
}
