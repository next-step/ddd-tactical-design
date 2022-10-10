package kitchenpos.menus.tobe.doubles;

import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MemoryMenuRepository implements MenuRepository {

    private final Map<UUID, Menu> store = new HashMap<>();

    @Override
    public <S extends Menu> S save(S entity) {
        UUID id = UUID.randomUUID();
        ReflectionTestUtils.setField(entity, "id", id);
        store.put(id, entity);
        return entity;
    }

    @Override
    public Optional<Menu> findById(UUID id) {
        return store.values()
                .stream()
                .filter(it -> it.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public List<Menu> findAllByIdIn(List<UUID> ids) {
        List<Menu> result = new ArrayList<>();
        for (Menu menu : store.values()) {
            if (ids.contains(menu.getId())) {
                result.add(menu);
            }
        }
        return result;
    }

    @Override
    public List<Menu> findAllByProductId(UUID productId) {
        List<Menu> result = new ArrayList<>();
        for (Menu menu : store.values()) {
            if (menu.containsProduct(productId)) {
                result.add(menu);
            }
        }
        return result;
    }
}
