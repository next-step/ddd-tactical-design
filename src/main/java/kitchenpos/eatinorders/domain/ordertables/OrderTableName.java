package kitchenpos.eatinorders.domain.ordertables;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String value;

    protected OrderTableName() {
    }

    public OrderTableName(final String value) {
        this.validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("매장 테이블의 이름은 필수로 입력해야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableName that = (OrderTableName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
