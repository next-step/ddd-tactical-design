package kitchenpos.eatinorders.tobe.domain.eatinorder;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTableId;
import kitchenpos.shared.domain.OrderType;

@Table(name = "orders")
@Entity(name = "TobeEatInOrder")
public class EatInOrder {

    public static final String ERROR_MESSAGE_VALUE_NULL = "값이 NULL 입니다.";
    public static final String ERROR_STATUS_NOT_WAITING = "대기중 상태에서만 변경 가능합니다.";
    public static final String ERROR_STATUS_NOT_ACCEPTED = "수락함 상태에서만 변경 가능합니다.";
    public static final String ERROR_STATUS_NOT_SERVED = "제공됨 상태에서만 변경 가능합니다.";

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "id"))
    private EatInOrderId id;

    @Column(name = "type", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private OrderType type = OrderType.EAT_IN;

    @Column(name = "status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "order_date_time"))
    private EatInOrderDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "order_table_id"))
    private RestaurantTableId restaurantTableId;

    public EatInOrder(UUID id, OrderType type, EatInOrderStatus status, LocalDateTime orderDateTime,
        List<EatInOrderLineItem> eatInOrderLineItems, UUID restaurantTableId) {
        this.id = new EatInOrderId(id);
        validateType(type);
        this.type = type;
        this.status = status;
        this.orderDateTime = new EatInOrderDateTime(orderDateTime);
        this.eatInOrderLineItems = new EatInOrderLineItems(eatInOrderLineItems);
        this.restaurantTableId = new RestaurantTableId(restaurantTableId);
    }

    protected EatInOrder() {
    }

    private static void validateType(OrderType type) {
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_VALUE_NULL);
        }
    }

    public UUID getId() {
        return id.getValue();
    }

    public OrderType getType() {
        return type;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime.getValue();
    }

    public List<EatInOrderLineItem> getOrderLineItems() {
        return eatInOrderLineItems.getValue();
    }

    public UUID getRestaurantTableId() {
        return restaurantTableId.getValue();
    }

    public void accepted() {
        validateStatus(EatInOrderStatus.WAITING, ERROR_STATUS_NOT_WAITING);
        this.status = EatInOrderStatus.ACCEPTED;
    }

    public void served() {
        validateStatus(EatInOrderStatus.ACCEPTED, ERROR_STATUS_NOT_ACCEPTED);
        this.status = EatInOrderStatus.SERVED;
    }

    public void completed() {
        validateStatus(EatInOrderStatus.SERVED, ERROR_STATUS_NOT_SERVED);
        this.status = EatInOrderStatus.COMPLETED;
    }

    private void validateStatus(EatInOrderStatus status, String message) {
        if (this.status != status) {
            throw new IllegalStateException(message);
        }
    }
}
