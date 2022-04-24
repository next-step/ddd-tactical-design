package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.OrderLineItem;
import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderLineItem;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;

import java.util.List;

public class EatInOrderRegisterRequest {
    private List<TobeOrderLineItem> orderLineItem;
    private OrderTableId orderTableId;

    public EatInOrderRegisterRequest(List<TobeOrderLineItem> orderLineItem, OrderTableId orderTableId) {
        this.orderLineItem = orderLineItem;
        this.orderTableId = orderTableId;
    }

    public EatInOrderRegisterRequest() {
    }

    public List<TobeOrderLineItem> getOrderLineItem() {
        return orderLineItem;
    }

    public void setOrderLineItem(List<TobeOrderLineItem> orderLineItem) {
        this.orderLineItem = orderLineItem;
    }

    public OrderTableId getOrderTableId() {
        return orderTableId;
    }

    public void setOrderTableId(OrderTableId orderTableId) {
        this.orderTableId = orderTableId;
    }
}
