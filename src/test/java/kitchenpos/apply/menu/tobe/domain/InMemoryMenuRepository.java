package kitchenpos.apply.menu.tobe.domain;

import kitchenpos.apply.menus.tobe.domain.Menu;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMenuRepository implements MenuRepository {
    private final Map<UUID, Menu> menus = new HashMap<>();

    @Override
    public Menu save(final Menu menu) {
        menus.put(UUID.fromString(menu.getId()), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public boolean existsByIdAndDisplayedWithPrice(UUID id, BigDecimal price) {
        return menus.values().stream()
                .anyMatch(it -> UUID.fromString(it.getId()).equals(id)
                        && it.getPrice().equals(price)
                        && it.isDisplayed());
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findAllByIdIn(final List<UUID> ids) {
        return menus.values().stream()
            .filter(menu -> ids.contains(UUID.fromString(menu.getId())))
            .collect(Collectors.toList());
    }

    @Override
    public List<Menu> findAllByProductId(final UUID productId) {
        return menus.values()
            .stream()
            .filter(menu -> menu.getMenuProductList().stream()
                    .anyMatch(menuProduct -> UUID.fromString(menuProduct.getProductId()).equals(productId)))
            .collect(Collectors.toList());
    }
}
