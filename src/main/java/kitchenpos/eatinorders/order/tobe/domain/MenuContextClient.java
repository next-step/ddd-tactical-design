package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.eatinorders.order.tobe.domain.vo.EatInOrderMenu;

import java.util.UUID;

public interface MenuContextClient {

    EatInOrderMenu findOrderMenuItemById(final UUID id);
}
