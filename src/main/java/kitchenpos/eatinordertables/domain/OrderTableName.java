package kitchenpos.eatinordertables.domain;

import kitchenpos.common.infra.Profanities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderTableName {
    @Column(name = "name", nullable = false)
    private String name;

    protected OrderTableName() {}

    public OrderTableName(final String name, final Profanities profanities) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("이름은 필수값입니다.");
        }
        if (profanities.contains(name)) {
            throw new IllegalArgumentException("이름에는 비속어가 포함될 수 없습니다.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
