package kitchenpos.deliveryorders.domain;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "eat_in_orders")
@Entity
public class DeliveryOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderMasterId orderMasterId;

    @Embedded
    private DeliveryAddress deliveryAddress;

    protected DeliveryOrder() {
    }

    public DeliveryOrder(OrderMasterId orderMasterId, DeliveryAddress deliveryAddress) {
        if (orderMasterId == null) {
            throw new IllegalArgumentException("주문 master 가 없으면 등록 할 수 없습니다.");
        }
        this.orderMasterId = orderMasterId;
        this.deliveryAddress = deliveryAddress;
    }

    public UUID getId() {
        return id;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public BigDecimal getTotalOrderPrice() {
        return orderMasterId.getTotalOrderPrice();
    }
}
