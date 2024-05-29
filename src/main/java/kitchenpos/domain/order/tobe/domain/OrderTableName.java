package kitchenpos.domain.order.tobe.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import kitchenpos.domain.support.Name;

@Embeddable
class OrderTableName {
    @Embedded
    private Name name;

    protected OrderTableName() {
    }

    public OrderTableName(Name name) {
        if (name != null && name.getName().isEmpty()) {
            throw new IllegalArgumentException("name은 빈 값일 수 없습니다.");
        }
        this.name = name;
    }

    public String getName() {
        return name.getName();
    }
}
