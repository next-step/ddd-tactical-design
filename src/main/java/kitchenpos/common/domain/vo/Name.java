package kitchenpos.common.domain.vo;

import kitchenpos.common.domain.vo.exception.InvalidNameException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    @Column(name = "name", nullable = false)
    private String value;

    protected Name() {
    }

    private Name(final String value) {
        this.value = value;
    }

    public static Name valueOf(final String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new InvalidNameException();
        }
        return new Name(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Name that = (Name) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
