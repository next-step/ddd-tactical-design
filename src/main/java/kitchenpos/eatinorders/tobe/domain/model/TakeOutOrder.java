package kitchenpos.eatinorders.tobe.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

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

    public void accept() {
        if (this.status != TakeOutOrderStatus.WAITING) {
            throw new IllegalStateException();
        }

        this.status = TakeOutOrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != TakeOutOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }

        this.status = TakeOutOrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != TakeOutOrderStatus.SERVED) {
            throw new IllegalStateException();
        }

        this.status = TakeOutOrderStatus.COMPLETED;
    }

    public TakeOutOrderStatus getStatus() {
        return status;
    }
}
