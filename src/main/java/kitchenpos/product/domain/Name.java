package kitchenpos.product.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.logging.log4j.util.Strings;

public final class Name {

    private final String value;

    public Name(final String value) {
        checkArgument(Strings.isNotEmpty(value), "name must not be empty. value: %s", value);

        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Name name = (Name) o;
        return Objects.equal(value, name.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("value", value)
            .toString();
    }

    public String getValue() {
        return value;
    }
}
