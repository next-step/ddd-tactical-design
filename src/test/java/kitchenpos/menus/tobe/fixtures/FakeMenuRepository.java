package kitchenpos.menus.tobe.fixtures;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;

import java.util.*;

public class FakeMenuRepository implements MenuRepository {

    private Map<UUID, Menu> inMemory = new HashMap<>();

    @Override
    public Menu save(Menu menu) {
        inMemory.put(menu.getId(), menu);
        return inMemory.get(menu.getId());
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return Optional.ofNullable(inMemory.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return inMemory.values().stream().toList();
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return inMemory.values().stream().filter(menu -> ids.contains(menu.getId())).toList();
    }

    @Override
    public List<Menu> findAllByProductId(UUID productId) {
        return inMemory.values()
                .stream()
                .filter(menu ->
                        menu.getMenuProducts()
                            .stream()
                            .anyMatch(menuProduct -> menuProduct.getProductId().equals(productId)))
                .toList();
    }
}
