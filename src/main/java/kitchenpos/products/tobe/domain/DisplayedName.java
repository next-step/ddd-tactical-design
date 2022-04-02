package kitchenpos.products.tobe.domain;

import java.util.Objects;

public final class DisplayedName {

    private final String value;

    public DisplayedName(String value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DisplayedName)) {
            return false;
        }
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "DisplayedName{" +
            "value='" + value + '\'' +
            '}';
    }
}
