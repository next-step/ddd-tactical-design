package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.eatinorders.tobe.dto.MenuResponse;

import java.util.List;
import java.util.UUID;

public interface MenuTranslator {
    MenuResponse getMenu(final OrderLineItem orderLineItem);

    List<MenuResponse> getMenus(final List<UUID> menuIds);

    void validateOrderLineItem(OrderLineItem orderLineItem);
}
