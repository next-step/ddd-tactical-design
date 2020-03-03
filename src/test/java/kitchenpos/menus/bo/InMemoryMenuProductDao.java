package kitchenpos.menus.bo;

import kitchenpos.menus.dao.MenuProductDao;
import kitchenpos.menus.model.MenuProduct;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMenuProductDao implements MenuProductDao {
    private final Map<Long, MenuProduct> entities = new HashMap<>();

    @Override
    public MenuProduct save(final MenuProduct entity) {
        entities.put(entity.getSeq(), entity);
        return entity;
    }

    @Override
    public Optional<MenuProduct> findById(final Long id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public List<MenuProduct> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public List<MenuProduct> findAllByMenuId(final Long menuId) {
        return entities.values()
                .stream()
                .filter(entity -> Objects.equals(entity.getMenuId(), menuId))
                .collect(Collectors.toList())
                ;
    }
}
