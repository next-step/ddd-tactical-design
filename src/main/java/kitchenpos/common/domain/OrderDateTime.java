package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
public class OrderDateTime extends ValueObject {

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime value;

    public OrderDateTime(LocalDateTime localDateTime) {
        this.value = localDateTime;
    }

    protected OrderDateTime() {

    }

    public static OrderDateTime now(){
        return new OrderDateTime(LocalDateTime.now());
    }


    public LocalDateTime getValue() {
        return value;
    }
}
