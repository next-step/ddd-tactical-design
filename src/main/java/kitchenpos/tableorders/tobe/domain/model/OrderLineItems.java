package kitchenpos.tableorders.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;
import kitchenpos.tableorders.tobe.domain.dto.CreatOrderLineItemCommand;
import kitchenpos.tableorders.tobe.domain.dto.MenuResponse;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderLineItems {

    private List<OrderLineItem> elements;

    public OrderLineItems(List<CreatOrderLineItemCommand> items, Map<UUID, MenuResponse> menus) {
        if (items.size() != menus.size()) {
            throw new IllegalArgumentException("메뉴가 존재하지 않습니다.");
        }
        elements = items.stream()
                .map(it -> new OrderLineItem(menus.get(it.getMenuId()), new Price(it.getPrice()), it.getQuantity()))
                .collect(Collectors.toList());
    }

}
