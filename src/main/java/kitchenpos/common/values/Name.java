package kitchenpos.common.values;

import kitchenpos.common.domain.Purgomalum;
import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    @Column(name = "name", nullable = false)
    private String value;


    protected Name() {
    }

    public Name(final String value, final Purgomalum purgomalum) {
        validate(value, purgomalum);
        this.value = value;
    }

    private static void validate(String value, Purgomalum purgomalum) {
        if (value == null || value.isEmpty()) {
            String message = String.format("이름이 %s 이므로", value);
            throw new KitchenPosException(message, KitchenPosExceptionType.BAD_REQUEST);
        }
        if (purgomalum.containsProfanity(value)) {
            String message = String.format("이름=%s 에 비속어가 포함되어 있으므로", value);
            throw new KitchenPosException(message, KitchenPosExceptionType.BAD_REQUEST);
        }
    }

    public String getValue() {
        return value;
    }

    public boolean equalValue(String value) {
        return this.value.equals(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return Objects.equals(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
