package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.eatinorders.order.tobe.domain.vo.MenuSpecification;

import java.util.UUID;

public interface MenuContextClient {

    MenuSpecification findOrderMenuItemById(final UUID id);
}
