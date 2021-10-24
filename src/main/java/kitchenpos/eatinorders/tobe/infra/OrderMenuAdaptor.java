package kitchenpos.eatinorders.tobe.infra;


import kitchenpos.eatinorders.tobe.domain.Menu;

import java.util.List;
import java.util.UUID;

public interface OrderMenuAdaptor {
    List<Menu> findAllByIdIn(List<UUID> ids);
}
