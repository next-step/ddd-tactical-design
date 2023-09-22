package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
class Name {
    @Column(name = "name", nullable = false)
    private String value;

    protected Name() {
    }

    private Name(final String value) {
        this.value = value;
    }

    public static Name from(final String value, final ProductNamePolicy productNamePolicy) {
        Name name = new Name(value);
        productNamePolicy.validateDisplayName(name);
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name that = (Name) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
