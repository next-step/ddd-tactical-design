package kitchenpos.deliveryorders.domain;

import static kitchenpos.deliveryorders.domain.DeliveryOrderStatus.*;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.deliveryorders.infra.KitchenridersClient;

@Table(name = "delivery_orders")
@Entity
public class DeliveryOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private DeliveryOrderLineItems orderLineItems;

    @Embedded
    private DeliveryAddress deliveryAddress;

    protected DeliveryOrder() {
    }

    public DeliveryOrder(DeliveryOrderLineItems orderLineItems, DeliveryAddress deliveryAddress) {
        this.id = UUID.randomUUID();
        this.orderLineItems = orderLineItems;
        this.deliveryAddress = deliveryAddress;
        this.status = initialOrderStatus();
    }

    public UUID getId() {
        return id;
    }

    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void accept(KitchenridersClient client) {
        if (status != DeliveryOrderStatus.WAITING) {
            throw new IllegalStateException("접수 대기 중인 주문만 접수할 수 있다.");
        }
        status = status.nextStatus();
        client.requestDelivery(id, orderLineItems.sumOfOrderPrice(), deliveryAddress.getValue());
    }

    public void serve() {
        if (status != DeliveryOrderStatus.ACCEPTED) {
            throw new IllegalStateException("접수된 주문만 서빙할 수 있다.");
        }
        status = status.nextStatus();
    }

    public void startDelivery() {
        if (status != DeliveryOrderStatus.SERVED) {
            throw new IllegalStateException("서빙된 주문만 배달할 수 있다.");
        }
        status = status.nextStatus();
    }

    public void completeDelivery() {
        if (status != DeliveryOrderStatus.DELIVERING) {
            throw new IllegalStateException("배달 중인 주문만 배달 완료할 수 있다.");
        }
        status = status.nextStatus();
    }

    public void complete() {
        if (status != DeliveryOrderStatus.DELIVERED) {
            throw new IllegalStateException("배달 완료된 주문만 완료할 수 있다.");
        }
        status = status.nextStatus();
    }

    public boolean isSameStatus(DeliveryOrderStatus orderStatus) {
        return status == orderStatus;
    }

}
