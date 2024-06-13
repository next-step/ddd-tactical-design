package kitchenpos.eatinorders.tobe.domain.order.createsupporter;

import java.util.List;
import java.util.UUID;

public interface RegisteredMenuReader {

  List<RegisteredMenu> findAllByIdIn(List<UUID> menuIds);
}
