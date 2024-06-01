package kitchenpos.orders.tobe.application.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.menus.tobe.domain.Menu;

@Service
public class OrderMenuServiceAdapter implements MenuServiceAdapter {
    private final MenuService menuService;

    public OrderMenuServiceAdapter(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return menuService.findAllByIdIn(ids);
    }
}
