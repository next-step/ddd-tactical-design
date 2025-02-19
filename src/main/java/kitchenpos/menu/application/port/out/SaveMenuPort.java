package kitchenpos.menu.application.port.out;

import kitchenpos.menu.domain.model.Menu;

import java.util.List;

public interface SaveMenuPort {
    Menu save(Menu menu);
    void saveAll(List<Menu> menus);
}
