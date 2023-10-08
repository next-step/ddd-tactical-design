package kitchenpos.order.tobe.eatinorder.domain;

import java.util.List;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLineItemDto;

public interface MenuClient {

    List<Menu> getMenusByMenuIds(List<EatInOrderLineItemDto> orderLineItems);
}
