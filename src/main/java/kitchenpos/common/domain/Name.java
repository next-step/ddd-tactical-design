package kitchenpos.common.domain;

import kitchenpos.products.tobe.domain.Profanities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {
    private String value;

    protected Name() {
    }

    public Name(String value, Profanities profanities) {
        if (Objects.isNull(value) || profanities.contains(value)) {
            throw new IllegalArgumentException("이름이 올바르지 않으면 등록할 수 없습니다.");
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name that = (Name) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
