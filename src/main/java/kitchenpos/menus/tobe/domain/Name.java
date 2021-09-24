package kitchenpos.menus.tobe.domain;

import kitchenpos.common.Profanities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    private String value;

    protected Name() {

    }

    public Name(String value, Profanities profanities) {
        if (Objects.isNull(value) || profanities.contains(value)) {
            throw new IllegalArgumentException("올바르지 않은 이름으로 등록할 수 없습니다.");
        }

        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name name = (Name) o;
        return value.equals(name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
