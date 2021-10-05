package kitchenpos.eatinorders.tobe.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import kitchenpos.eatinorders.tobe.domain.handler.DomainExceptionHandler;

@Entity
@DiscriminatorValue("TakeOut")
public class TakeOutOrder extends Order {

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TakeOutOrderStatus status;

    public TakeOutOrder(List<OrderLineItem> orderLineItems) {
        super(OrderType.TAKEOUT, LocalDateTime.now(), orderLineItems);
        this.status = TakeOutOrderStatus.WAITING;
    }

    public void accept(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != TakeOutOrderStatus.WAITING) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = TakeOutOrderStatus.ACCEPTED;
    }

    public void serve(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != TakeOutOrderStatus.ACCEPTED) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);throw new IllegalStateException();
        }

        this.status = TakeOutOrderStatus.SERVED;
    }

    public void complete(DomainExceptionHandler domainExceptionHandler) {
        if (this.status != TakeOutOrderStatus.SERVED) {
            domainExceptionHandler.handle(ExceptionType.ILLEGAL_ORDER_STATUS);
        }

        this.status = TakeOutOrderStatus.COMPLETED;
    }

    public TakeOutOrderStatus getStatus() {
        return status;
    }
}
