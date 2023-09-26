package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.MenuEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MenuEventListener {

    private final MenuService menuService;

    public MenuEventListener(MenuService menuService) {
        this.menuService = menuService;
    }

    @EventListener
    public void changeMenuDisplayStatus(final MenuEvent menuEvent) {
        menuService.changeMenuDisplayStatus(menuEvent.getProductId());
    }
}
