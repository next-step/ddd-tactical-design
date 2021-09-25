package kitchenpos.eatinorders.tobe.domain.repository;

import kitchenpos.eatinorders.tobe.domain.model.OrderMenu;

import java.util.List;
import java.util.UUID;

public interface OrderMenuRepository {

    OrderMenu save(final OrderMenu orderMenu);

    List<OrderMenu> findAllByIdIn(final List<UUID> ids);
}
