package kitchenpos.common.tobe;

import java.util.Objects;

public class DisplayedName {

    private final String name;

    public DisplayedName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(final String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("이름은 필수고, 비워둘 수 없습니다");
        }
    }

    public String value() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final DisplayedName name = (DisplayedName) o;
        return this.name.equals(name.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
