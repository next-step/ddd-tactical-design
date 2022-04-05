package kitchenpos.eatinorders.tobe.eatinorder.infra;

import kitchenpos.eatinorders.tobe.eatinorder.application.MenuRepository;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.MenuResponse;
import kitchenpos.menus.tobe.menu.domain.Menu;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FakeMenuRepository implements MenuRepository {
    private ConcurrentHashMap<UUID, Menu> repository = new ConcurrentHashMap<>();

    public Menu save(final Menu menu) {
        repository.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public List<MenuResponse> findAllByIdn(List<UUID> menuIds) {
        return repository.values()
                .stream()
                .filter(menu -> menuIds.contains(menu.getId()))
                .map(menu -> new MenuResponse(menu.getId(), menu.getPrice(), menu.isDisplayed()))
                .collect(Collectors.toList());
    }
}
