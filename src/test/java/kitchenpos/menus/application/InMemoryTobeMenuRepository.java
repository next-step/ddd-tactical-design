package kitchenpos.menus.application;

import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.products.domain.tobe.domain.vo.ProductId;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTobeMenuRepository implements TobeMenuRepository {
    private final Map<MenuId, TobeMenu> menus = new HashMap<>();

    @Override
    public TobeMenu save(final TobeMenu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<TobeMenu> findById(final MenuId id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<TobeMenu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<TobeMenu> findAllByIdIn(final List<MenuId> ids) {
        return menus.values()
            .stream()
            .filter(menu -> ids.contains(menu.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<TobeMenu> findAllByProductId(final ProductId productId) {
        return menus.values()
            .stream()
            .filter(menu -> menu.getMenuProducts().stream().anyMatch(menuProduct -> menuProduct.getProduct().getId().equals(productId)))
            .collect(Collectors.toList());
    }
}
