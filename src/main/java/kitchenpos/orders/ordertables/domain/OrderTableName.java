package kitchenpos.orders.ordertables.domain;

import kitchenpos.common.domain.ValueObject;
import kitchenpos.orders.ordertables.exception.OrderTableErrorCode;
import kitchenpos.orders.ordertables.exception.OrderTableNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OrderTableName extends ValueObject {

    @Column(name = "name", nullable = false)
    private String value;

    protected OrderTableName() {

    }

    public OrderTableName(String value) {
        if (value == null || value.isBlank()) {
            throw new OrderTableNameException(OrderTableErrorCode.NAME_IS_EMTPY);
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
