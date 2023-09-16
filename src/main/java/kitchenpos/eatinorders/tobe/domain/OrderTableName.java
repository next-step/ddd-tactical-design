package kitchenpos.eatinorders.tobe.domain;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {

    public static OrderTableName INIT = new OrderTableName("임시테이블");

    @Column(name = "name", nullable = false)
    private String name;

    public OrderTableName(String name) {
        if (isEmpty(name)) {
            throw new IllegalArgumentException("주문테이블 이름은 비어있을수 없습니다");
        }

        this.name = name;
    }

    protected OrderTableName() {
    }

    public String getName() {
        return name;
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
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
