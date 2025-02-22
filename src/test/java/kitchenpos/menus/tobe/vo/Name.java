package kitchenpos.menus.tobe.vo;

import kitchenpos.products.tobe.domain.exception.InvalidMenuGroupNameException;

import java.util.Objects;

public class Name {

    private String value;

    public Name(final String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new InvalidMenuGroupNameException();
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Name that = (Name) o;
        return Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValue());
    }
}
