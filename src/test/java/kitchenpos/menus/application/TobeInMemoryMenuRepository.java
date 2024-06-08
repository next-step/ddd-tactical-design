package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.TobeMenu;
import kitchenpos.menus.tobe.domain.TobeMenuRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class TobeInMemoryMenuRepository implements TobeMenuRepository {
    private final Map<UUID, TobeMenu> menus = new HashMap<>();

    @Override
    public TobeMenu save(final TobeMenu menu) {
        menus.put(menu.id(), menu);
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
                .filter(menu -> ids.contains(menu.id()))
                .toList();
    }

    @Override
    public List<TobeMenu> findAllByProductId(final UUID productId) {
        return menus.values()
                .stream()
                .filter(menu -> menu.menuProducts().get(productId).isPresent())
                .toList();
    }
}
