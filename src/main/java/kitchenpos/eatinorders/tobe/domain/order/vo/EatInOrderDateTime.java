package kitchenpos.eatinorders.tobe.domain.order.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class EatInOrderDateTime {

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    protected EatInOrderDateTime() {
    }

    public EatInOrderDateTime(final LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EatInOrderDateTime that)) return false;
        return Objects.equals(orderDateTime, that.orderDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDateTime);
    }
}
