package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractOrder {

    @Column(name = "order_date_time", nullable = false)
    protected LocalDateTime orderDateTime;

    protected AbstractOrder() {
        this.orderDateTime = LocalDateTime.now();
    }

    protected AbstractOrder(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public abstract LocalDateTime getOrderDateTime();
}
