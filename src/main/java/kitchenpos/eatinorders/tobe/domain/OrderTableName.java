package kitchenpos.eatinorders.tobe.domain;

import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static kitchenpos.eatinorders.exception.OrderTableExceptionMessage.ORDER_TABLE_NAME_EMPTY;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String name;

    protected OrderTableName() {

    }

    private OrderTableName(String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException(ORDER_TABLE_NAME_EMPTY);
        }
        this.name = name;
    }

    public static OrderTableName create(String name) {
        return new OrderTableName(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableName that = (OrderTableName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
