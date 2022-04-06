package kitchenpos.common.domain;

import java.util.Objects;

public final class DisplayedName {

    private final String value;

    public DisplayedName(String value, DisplayedNamePolicy policy) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("DisplayedName 은 비어 있을 수 없습니다. value: %s", value)
            );
        }
        if (policy.hasProfanity(value)) {
            throw new IllegalArgumentException(
                String.format("비속어가 포함될 수 없습니다. value: %s", value)
            );
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
