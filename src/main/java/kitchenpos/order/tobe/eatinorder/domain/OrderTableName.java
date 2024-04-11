package kitchenpos.order.tobe.eatinorder.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {

    @Column(name = "name", nullable = false)
    private String value;

    protected OrderTableName() {
    }

    public OrderTableName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("주문 테이블 이름은 null이나 공백을 허용하지 않습니다.");
        }

        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderTableName)) {
            return false;
        }
        OrderTableName that = (OrderTableName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
