package kitchenpos.eatinorder.shared;

import kitchenpos.menus.tobe.domain.menu.Menu;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface MenuClient {
    Menu findByOrderLineMenuId(UUID menuId);
}
