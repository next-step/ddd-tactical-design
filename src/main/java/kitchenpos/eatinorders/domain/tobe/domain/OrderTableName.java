package kitchenpos.eatinorders.domain.tobe.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String name;

    protected OrderTableName() {
    }

    private OrderTableName(String name) {
        nullValidation(name);
        this.name = name;
    }

    public static OrderTableName of(String name) {
        return new OrderTableName(name);
    }

    private void nullValidation(String name) {
        if (name == "" || Objects.isNull(name)) {
            throw new IllegalArgumentException("이름은 필수로 입력되야 합니다.");
        }
    }
}
