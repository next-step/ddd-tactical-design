package kitchenpos.eatinorders.tobe.domain.eatinorder.domain;

import kitchenpos.eatinorders.model.OrderStatus;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
@Access(AccessType.FIELD)
public class OrderInfo {

    private Long orderTableId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public OrderInfo() {
    }

    OrderInfo(Long orderTableId) {
        this(orderTableId, null);
    }

    OrderInfo(OrderStatus orderStatus) {
        this(null, orderStatus);
    }

    private OrderInfo(Long orderTableId, OrderStatus orderStatus) {
        this.orderTableId = orderTableId;
        this.orderStatus = orderStatus;
    }

    public static OrderInfo createCooking(Long orderTableId) {
        return new OrderInfo(orderTableId, OrderStatus.COOKING);
    }

    public Long getOrderTableId() {
        return orderTableId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    OrderInfo changeOrderStatus() {
        if (this.orderStatus.isCompleted()) {
            throw new IllegalArgumentException();
        }
        return new OrderInfo(orderTableId, orderStatus);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderInfo orderInfo = (OrderInfo) o;
        return Objects.equals(orderTableId, orderInfo.orderTableId) &&
                orderStatus == orderInfo.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderTableId, orderStatus);
    }
}
