package kitchenpos.eatinorders.tobe.domain.orderTable.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidTableNameException;

import java.util.Objects;

@Embeddable
public class TableName {

    @Column(name = "name", nullable = false)
    private String name;

    protected TableName() {
    }

    public TableName(final String name) {

        if (Objects.isNull(name) || name.isBlank()) {
            throw new InvalidTableNameException("주문 테이블이 존재해야 합니다.");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableName tableName)) return false;
        return Objects.equals(name, tableName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
