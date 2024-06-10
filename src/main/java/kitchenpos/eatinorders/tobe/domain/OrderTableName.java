package kitchenpos.eatinorders.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class OrderTableName {

    @Column(name = "name", nullable = false)
    private final String value;

    public OrderTableName() {
        this(new String());
    }

    public OrderTableName(String value) {
        checkName(value);
        this.value = value;
    }

    private void checkName(String value){
        if(Objects.isNull(value) || value.isBlank()){
            throw new IllegalArgumentException("주문테이블 이름이 필요합니다.");
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
