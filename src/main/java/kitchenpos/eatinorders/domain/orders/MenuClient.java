package kitchenpos.eatinorders.domain.orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MenuClient {

    boolean isHide(UUID menuId);

    BigDecimal getMenuPrice(UUID menuId);

    OrderedMenus getOrderedMenuByMenuIds(List<UUID> menuIds);
}
