package kitchenpos.menus.tobe.domain;

import static java.util.stream.Collectors.toList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemoryMenuProductRepository implements MenuProductRepository {

    private Long idSeq = 0L;
    private final Map<Long, MenuProduct> menuProductMap;

    public InMemoryMenuProductRepository() {
        menuProductMap = new HashMap<>();
    }


    @Override
    public MenuProduct save(MenuProduct entity) {
        menuProductMap.put(idSeq++, entity);
        return entity;
    }

    @Override
    public List<MenuProduct> findAllByProductId(UUID productId) {
        return menuProductMap.values()
            .stream()
            .filter(menuProduct -> menuProduct.getProductId().equals(productId))
            .collect(toList());
    }
}
