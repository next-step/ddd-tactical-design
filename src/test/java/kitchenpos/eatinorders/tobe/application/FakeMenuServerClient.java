package kitchenpos.eatinorders.tobe.application;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.MenuServerClient;
import kitchenpos.eatinorders.tobe.dto.MenuDTO;

public class FakeMenuServerClient implements MenuServerClient {

    private final List<MenuDTO> menuDTOS;

    public FakeMenuServerClient(List<MenuDTO> menuDTOS) {
        this.menuDTOS = menuDTOS;
    }

    @Override
    public List<MenuDTO> findDisplayedMenuAllByIds(List<UUID> menuIds) {
        return menuDTOS;
    }
}
