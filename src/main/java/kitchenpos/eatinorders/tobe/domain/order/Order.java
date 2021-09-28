package kitchenpos.eatinorders.tobe.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.OrderId;
import kitchenpos.common.domain.OrderTableId;
import kitchenpos.common.domain.Price;
import kitchenpos.eatinorders.domain.OrderStatus;

@Table(name = "orders")
@Entity
public class Order {

    @EmbeddedId
    @Column(name = "id", columnDefinition = "varbinary(16)")
    private OrderId id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Embedded
    @AttributeOverride(
        name = "value",
        column = @Column(name = "order_table_id", columnDefinition = "varbinary(16)")
    )
    private OrderTableId orderTableId;

    protected Order() {
    }

    public Order(
        final OrderId id,
        final OrderLineItems orderLineItems,
        final OrderTableId orderTableId,
        final OrderValidator orderValidator
    ) {
        this.id = id;
        this.status = OrderStatus.WAITING;
        this.orderDateTime = LocalDateTime.now();
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;

        orderValidator.validate(this);
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException("접수 대기 중인 매장 주문만 접수할 수 있습니다.");
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("접수된 매장 주문만 서빙할 수 있습니다.");
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete(final OrderTableManager orderTableManager) {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException("서빙된 매장 주문만 완료할 수 있습니다.");
        }
        this.status = OrderStatus.COMPLETED;

        orderTableManager.clear(this.orderTableId);
    }

    public List<MenuId> getMenuIds() {
        return this.orderLineItems.getMenuIds();
    }

    public boolean isPriceValid(MenuId menuId, Price menuPrice) {
        return this.orderLineItems.isPriceValid(menuId, menuPrice);
    }

    public OrderTableId getOrderTableId() {
        return this.orderTableId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
