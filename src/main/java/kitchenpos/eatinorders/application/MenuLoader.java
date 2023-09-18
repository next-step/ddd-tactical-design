package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.OrderedMenu;

import java.util.UUID;

public interface MenuLoader {
    OrderedMenu findMenuById(UUID menuId);
}
