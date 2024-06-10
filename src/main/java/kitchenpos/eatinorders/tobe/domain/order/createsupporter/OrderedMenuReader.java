package kitchenpos.eatinorders.tobe.domain.order.createsupporter;

import java.util.List;
import java.util.UUID;

public interface OrderedMenuReader {

  List<OrderedMenu> findAllByIdIn(List<UUID> menuIds);
}
