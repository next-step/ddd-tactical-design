package kitchenpos.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;

import java.util.*;

public class FakeMenuRepository implements MenuRepository {

    private final HashMap<UUID, Menu> inMemory = new HashMap<>();

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return ids.stream()
                .filter(inMemory::containsKey)
                .map(inMemory::get)
                .toList();
    }

    @Override
    public List<Menu> findAllByProductId(UUID productId) {
        return inMemory.values()
                .stream()
                .filter(menu -> menu
                        .getMenuProducts()
                        .stream()
                        .anyMatch(menuProduct
                                -> menuProduct
                                .getProduct()
                                .getId().equals(productId)))
                .toList();
    }

    @Override
    public Menu save(Menu menu) {
        inMemory.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return Optional.ofNullable(inMemory.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(inMemory.values());
    }
}
