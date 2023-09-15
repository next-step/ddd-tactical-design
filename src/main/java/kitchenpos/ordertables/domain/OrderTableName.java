package kitchenpos.ordertables.domain;

import kitchenpos.common.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class OrderTableName extends ValueObject {

    @Column(name = "name", nullable = false)
    private String value;

    protected OrderTableName() {

    }

    public OrderTableName(String value) {
        this.value = value;
    }

}
