package kitchenpos.menus.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuProductRepository;

public class InMemoryMenuProductRepository implements MenuProductRepository {

    private final Map<Long, MenuProduct> menuProducts = new HashMap<>();

    @Override
    public List<MenuProduct> findAllByProductId(UUID productId) {
        return menuProducts.values()
            .stream()
            .filter(it -> it.getProductId() == productId)
            .collect(Collectors.toList());
    }
}
