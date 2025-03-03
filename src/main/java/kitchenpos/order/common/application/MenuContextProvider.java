package kitchenpos.order.common.application;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;

public interface MenuContextProvider {
    BigDecimal getPrice(MenuId menuId);

    List<MenuInfo> findMenus(List<MenuId> menuIds);

}
