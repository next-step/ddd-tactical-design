package kitchenpos.eatinorders.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName {
    private static final String ORDER_TABLE_NAME_MUST_NOT_BE_EMPTY = "주문 테이블 이름은 빈 값이 아니어야 합니다. 입력 값 : %s";

    @Column(name = "name", nullable = false)
    private final String name;

    protected OrderTableName() {
        this.name = null;
    }

    protected OrderTableName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException(String.format(ORDER_TABLE_NAME_MUST_NOT_BE_EMPTY, name));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableName that = (OrderTableName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
