package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.menu.TobeMenu;
import kitchenpos.menus.tobe.domain.menu.TobeMenuRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryTobeMenuRepository implements TobeMenuRepository {
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
            .filter(menu -> menu.getTobeMenuProducts().stream()
                                .anyMatch(menuProduct -> menuProduct.getProduct()
                                                                    .getId()
                                                                    .equals(productId)))
            .collect(Collectors.toList());
    }
}
