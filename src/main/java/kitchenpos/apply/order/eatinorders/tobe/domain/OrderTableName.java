package kitchenpos.apply.order.eatinorders.tobe.domain;

import kitchenpos.support.domain.ValueObject;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName extends ValueObject {

    @Column(name = "name", nullable = false)
    private String value;

    protected OrderTableName() { }

    public OrderTableName(String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("테이블 이름은 비어 있을 수 없다.");
        }

        this.value = name;
    }

    public String value() {
        return value;
    }

}
