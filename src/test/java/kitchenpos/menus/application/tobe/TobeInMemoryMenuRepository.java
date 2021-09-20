package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.TobeMenu;
import kitchenpos.menus.tobe.domain.TobeMenuRepository;

import java.util.*;
import java.util.stream.Collectors;

public class TobeInMemoryMenuRepository implements TobeMenuRepository {
    private final Map<UUID, TobeMenu> menus = new HashMap<>();

    @Override
    public TobeMenu save(final TobeMenu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<TobeMenu> findById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<TobeMenu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<TobeMenu> findAllByIdIn(final List<UUID> ids) {
        return menus.values()
            .stream()
            .filter(menu -> ids.contains(menu.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<TobeMenu> findAllByProductId(final UUID productId) {
        return menus.values()
            .stream()
            .filter(
                    menu -> menu.getMenuProducts()
                            .getMenuProducts()
                            .stream()
                            .anyMatch(menuProduct -> menuProduct.getProduct().getId().equals(productId))
            )
            .collect(Collectors.toList());
    }
}
