package kitchenpos.orders.eatinorders.application.loader;

import kitchenpos.orders.eatinorders.domain.OrderedMenu;

import java.util.UUID;

public interface MenuLoader {
    OrderedMenu findMenuById(UUID menuId);
}
