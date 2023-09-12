package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMenuRepository implements MenuRepository {

    private final Map<String, Menu> menus = new HashMap<>();

    @Override
    public Menu save(Menu menu) {
        return menus.put(menu.getId(), menu);
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return Optional.ofNullable(menus.get(id.toString()));
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
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        return menus.values().stream()
                .filter(it -> ids.contains(UUID.fromString(it.getId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Menu> findAllByProductId(UUID productId) {
        return menus.values().stream()
                .filter(it -> it.getMenuProductList()
                        .stream()
                        .anyMatch(its -> UUID.fromString(its.getProductId()).equals(productId))
                )
                .collect(Collectors.toList());
    }
}
