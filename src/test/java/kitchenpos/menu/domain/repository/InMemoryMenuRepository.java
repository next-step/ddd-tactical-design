package kitchenpos.menu.domain.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.product.domain.model.ProductId;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<MenuId, Menu> menus = new HashMap<>();

    @Override
    public List<Menu> findAllByMenuIdIn(List<MenuId> ids) {
        return menus.values().stream()
            .filter(menu -> ids.contains(menu.getMenuId()))
            .toList();
    }

    @Override
    public List<Menu> findAllByProductId(ProductId productId) {
        return menus.values().stream()
            .filter(menu -> menu.getMenuProducts().get().stream()
                .anyMatch(menuProduct -> menuProduct.getProductId().equals(productId)))
            .toList();
    }

    @Override
    public Menu save(Menu menu) {
        final var menuId = menu.getMenuId();
        menus.put(menuId, new Menu(menuId, menu.getName(), menu.getPrice(), menu.getMenuGroupId(), menu.isDisplayed(), menu.getMenuProducts()));
        return menu;
    }

    @Override
    public Optional<Menu> findByMenuId(MenuId id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }
}
