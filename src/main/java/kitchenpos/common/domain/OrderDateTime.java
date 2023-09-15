package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class OrderDateTime extends ValueObject {
    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime value;

    public OrderDateTime() {
        this.value = LocalDateTime.now();
    }

    public OrderDateTime(LocalDateTime value) {
        this.value = value;
    }

    public LocalDateTime getValue() {
        return value;
    }
}
