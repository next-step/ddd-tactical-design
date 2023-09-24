package kitchenpos.eatinorders.tobe.domain;

import java.util.List;
import java.util.UUID;

public interface TobeMenuClient {
    OrderMenu getOrderMenu(UUID id);

    List<OrderMenu> findAllByIdIn(List<UUID> ids);
}
