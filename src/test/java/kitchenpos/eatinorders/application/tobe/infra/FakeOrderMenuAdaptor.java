package kitchenpos.eatinorders.application.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.Menu;
import kitchenpos.eatinorders.tobe.infra.OrderMenuAdaptor;

import java.util.*;
import java.util.stream.Collectors;

public class FakeOrderMenuAdaptor implements OrderMenuAdaptor {
    private Map<UUID, Menu> menus = new HashMap<>();

    public void save(List<Menu> menus) {
        for (Menu menu : menus) {
            this.menus.put(menu.getId(), menu);
        }
    }

    @Override
    public List<Menu> menufindAllByIdIn(List<UUID> ids) {
        return menus.values().stream()
                .filter(menu -> ids.contains(menu.getId()))
                .collect(Collectors.toList());
    }
}
