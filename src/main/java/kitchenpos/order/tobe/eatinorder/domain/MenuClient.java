package kitchenpos.order.tobe.eatinorder.domain;

import java.util.List;
import java.util.UUID;

public interface MenuClient {
    List<MenuDto> findMenusByIds(List<UUID> menuIds);

}
