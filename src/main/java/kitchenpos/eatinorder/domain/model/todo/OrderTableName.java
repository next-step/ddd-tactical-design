package kitchenpos.eatinorder.domain.model.todo;

import kitchenpos.shared.domain.Profanities;

import java.util.Objects;

public class OrderTableName {
    private final String name;

    private OrderTableName(final String name) {
        this.name = name;
    }

    public static OrderTableName of(final String name, final Profanities profanities) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("주문 테이블 이름은 필수 입력값입니다.");
        }
        if (profanities.contains(name)) {
            throw new IllegalArgumentException("주문 테이블 이름에는 비속어를 사용할 수 없습니다. name: " + name);
        }
        return new OrderTableName(name);
    }

    public String value() {
        return name;
    }

    public boolean isSameAs(final String name) {
        return Objects.equals(this.name, name);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderTableName that = (OrderTableName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
