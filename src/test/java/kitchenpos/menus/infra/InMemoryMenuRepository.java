package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuId;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.domain.ProductId;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMenuRepository implements MenuRepository {

    private Map<MenuId, Menu> menus = new ConcurrentHashMap<>();

    @Override
    public Menu save(Menu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(MenuId id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findAllByIdIn(List<MenuId> ids) {
        return menus.values()
                .stream()
                .filter(menu -> ids.contains(menu.getId()))
                .toList();
    }

    @Override
    public List<Menu> findAllByProductId(ProductId productId) {
        return menus.values()
                .stream()
                .filter(menu -> menu.containProduct(productId))
                .toList();
    }
}
