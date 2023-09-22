package kitchenpos.menu.fakerepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menu.application.menu.port.out.MenuRepository;
import kitchenpos.menu.domain.menu.MenuNew;

public class MenuFakeRepository implements MenuRepository {

    private final Map<UUID, MenuNew> menus = new HashMap<>();

    @Override
    public MenuNew save(final MenuNew entity) {
        menus.put(entity.getId(), entity);

        return entity;
    }

    @Override
    public Optional<MenuNew> findById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<MenuNew> findAll() {
        return menus.values()
            .stream()
            .collect(Collectors.toUnmodifiableList());
    }


    @Override
    public List<MenuNew> findAllByProductId(final UUID productId) {
        return menus.values()
            .stream()
            .filter(menu -> hasProduct(menu, productId))
            .collect(Collectors.toUnmodifiableList());
    }

    private boolean hasProduct(final MenuNew menu, final UUID targetProductId) {
        return menu.getMenuProducts()
            .getValues()
            .stream()
            .anyMatch(menuProduct -> targetProductId.equals(menuProduct.getProductId()));
    }
}
