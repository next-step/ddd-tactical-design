package kitchenpos.order.tobe.eatinorder.domain.ordertable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class TableName {
    @Column(name = "name", nullable = false)
    private String name;

    protected TableName() {
    }

    public TableName(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("테이블 이름이 비어 있습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TableName tableName = (TableName) o;
        return Objects.equals(name, tableName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
