package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderId;

import javax.validation.constraints.NotNull;

public class OrderStatusChangeRequest {
    @NotNull(message = "변경하려는 주문을 선택해 주세요.")
    private OrderId orderId;
    @NotNull(message = "변경하려는 주문 상태를 선택해 주세요.")
    private OrderStatus status;
    @NotNull(message = "변경하려는 주문의 타입을 선택해 주세요.")
    private OrderType type;

    public OrderStatusChangeRequest(OrderId orderId, OrderStatus status, OrderType type) {
        this.orderId = orderId;
        this.status = status;
        this.type = type;
    }

    public OrderStatusChangeRequest() {
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public boolean isWaiting() {
        return this.status == OrderStatus.WAITING;
    }

    public boolean isAccepted() {
        return this.status == OrderStatus.ACCEPTED;
    }

    public boolean isServed() {
        return this.status == OrderStatus.SERVED;
    }

    public boolean isDelivering() {
        return this.status == OrderStatus.DELIVERING;
    }

    public boolean isDelivered() {
        return this.status == OrderStatus.DELIVERED;
    }

    public boolean isCompleted() {
        return this.status == OrderStatus.COMPLETED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderStatusChangeRequest that = (OrderStatusChangeRequest) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (status != that.status) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
