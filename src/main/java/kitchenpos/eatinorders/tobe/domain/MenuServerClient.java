package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.dto.MenuDTO;

public interface MenuServerClient {

    List<MenuDTO> findDisplayedMenuAllByIds(List<UUID> menuIds);
}
