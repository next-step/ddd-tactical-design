package kitchenpos.eatinorders.tobe.domain.entity;

import jakarta.persistence.*;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Table(name = "orders2")
@Entity(name = "EatInOrder")
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

    private UUID orderTableId;

    protected EatInOrder() {}

    public EatInOrder(UUID id, EatInOrderType type, EatInOrderStatus status, LocalDateTime orderDateTime,
                      OrderLineItems orderLineItems, UUID orderTableId) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
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

    public boolean isComplete() {
        return status == EatInOrderStatus.COMPLETED;
    }

    public void updateMenuInOrders(UUID menuId, BigDecimal price, boolean displayed) {
        orderLineItems.updateMenuInOrders(menuId, price, displayed);
    }

    public Set<UUID> allMenuId() {
        return orderLineItems.allMenuId();
    }

    public boolean hasMenuId(UUID inputMenuId) {
        boolean hasMenuId = orderLineItems.allMenuId()
                .stream()
                .anyMatch(thisMenuId -> thisMenuId.equals(inputMenuId));

        return hasMenuId;
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
