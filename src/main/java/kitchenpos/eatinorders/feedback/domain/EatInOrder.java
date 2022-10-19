package kitchenpos.eatinorders.feedback.domain;

import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "eat_in_order")
public class EatInOrder extends AbstractAggregateRoot<EatInOrder> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private OrderStatus orderStatus;

    @Embedded
    private OrderLineItems orderLineItems;

    private Long orderTableId;

    private LocalDateTime orderTime;

    protected EatInOrder() {
    }

    public EatInOrder(List<OrderLineItem> orderLineItems, Long orderTableId, OrderCreatePolicy orderCreatePolicy) {
        orderCreatePolicy.validate(orderLineItems, orderTableId);
        this.orderStatus = OrderStatus.WAITING;
        this.orderLineItems = new OrderLineItems(orderLineItems);
        this.orderTime = LocalDateTime.now();
        this.orderTableId = orderTableId;
    }

    public void accept() {
        if (this.orderStatus != OrderStatus.WAITING) {
            throw new IllegalStateException("접수 대기 상태의 주문만 수락할 수 있습니다.");
        }
        this.orderStatus = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.orderStatus != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("접수 상태의 주문만 서빙할 수 있습니다.");
        }
        this.orderStatus = OrderStatus.SERVED;
    }

    public void complete() {
        if (this.orderStatus != OrderStatus.SERVED) {
            throw new IllegalStateException("서빙 완료 상태의 주문만 완료할 수 있습니다.");
        }
        this.orderStatus = OrderStatus.COMPLETED;
        registerEvent(new OrderCompletedEvent(this, id, orderTableId));
    }

    public Long getId() {
        return id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public Long getOrderTableId() {
        return orderTableId;
    }
}
