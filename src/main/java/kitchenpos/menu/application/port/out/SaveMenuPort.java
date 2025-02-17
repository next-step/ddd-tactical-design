package kitchenpos.menu.application.port.out;

import kitchenpos.menu.domain.model.Menu;

public interface SaveMenuPort {
    Menu save(Menu menu);
}
