package kitchenpos.eatinorder.shared;

import kitchenpos.menus.tobe.domain.menu.Menu;

import java.util.UUID;

public interface MenuClient {
    Menu findByOrderLineMenuId(UUID menuId);
}
