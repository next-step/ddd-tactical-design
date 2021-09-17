package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.domain.Menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MenuTranslator {
    Menu getMenu(UUID menuId, final BigDecimal orderLineItemPrice);

    List<Menu> getMenus(final List<UUID> menuIds);
}
