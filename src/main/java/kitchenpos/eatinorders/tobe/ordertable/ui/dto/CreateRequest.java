package kitchenpos.eatinorders.tobe.ordertable.ui.dto;

import kitchenpos.eatinorders.tobe.ordertable.domain.OrderTable;

public class CreateRequest {
    private final String name;

    public CreateRequest(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public OrderTable toEntity() {
        return new OrderTable(name);
    }
}
