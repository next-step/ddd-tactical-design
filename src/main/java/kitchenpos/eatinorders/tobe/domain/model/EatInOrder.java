package kitchenpos.eatinorders.tobe.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kitchenpos.eatinorders.tobe.domain.handler.DomainExceptionHandler;
import kitchenpos.eatinorders.tobe.domain.interfaces.event.EventPublisher;

@Entity
@DiscriminatorValue("EatIn")
public class EatInOrder extends Order {

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "order_table_id", nullable = false)
    private UUID orderTableId;

    public EatInOrder(List<OrderLineItem> orderLineItems, UUID orderTableId) {
        super(OrderType.EAT_IN, LocalDateTime.now(), orderLineItems);
        this.status = EatInOrderStatus.WAITING;
        this.orderTableId = orderTableId;
    }

    public void accept(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != EatInOrderStatus.WAITING) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = EatInOrderStatus.ACCEPTED;
    }

    public void serve(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != EatInOrderStatus.ACCEPTED) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = EatInOrderStatus.SERVED;
    }

    public void complete(EventPublisher eventPublisher, DomainExceptionHandler domainExceptionHandler) {
        if (this.status != EatInOrderStatus.SERVED) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = EatInOrderStatus.COMPLETED;
        eventPublisher.publishTableCleaningEvent(new TableCleanedEvent(orderTableId));
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public UUID getOrderTableId() {
        return orderTableId;
    }
}
