package kitchenpos.eatinorders.tobe.entity;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.constant.EatInOrderType;
import kitchenpos.eatinorders.tobe.service.EatInOrderDomainService;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderType type;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Transient
    private UUID orderTableId;

    public EatInOrder(UUID id, EatInOrderType type, EatInOrderStatus status, LocalDateTime orderDateTime,
                      OrderLineItems orderLineItems, UUID orderTableId,
                      EatInOrderDomainService orderDomainService) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
        checkOrderTableOccupied(orderDomainService);
    }

    public EatInOrder(UUID id, EatInOrderType type, EatInOrderStatus status, LocalDateTime orderDateTime,
                      OrderLineItems orderLineItems, UUID orderTableId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    private void checkOrderTableOccupied(EatInOrderDomainService orderDomainService) {
        if (orderDomainService.isNotOccupiedOrderTable(this)) {
            throw new IllegalStateException();
        }
    }

    public void accept() {
        if (status != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        status = EatInOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        status = EatInOrderStatus.SERVED;
    }

    public void complete() {
        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = EatInOrderStatus.COMPLETED;
    }

    public void complete(EatInOrderDomainService eatInOrderDomainService) {
        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = EatInOrderStatus.COMPLETED;
        eatInOrderDomainService.clearOrderTable(this);
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderType getType() {
        return type;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
