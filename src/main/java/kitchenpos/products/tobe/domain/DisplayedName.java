package kitchenpos.products.tobe.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String value;

    protected DisplayedName() {
    }

    public DisplayedName(String name) {
        validate(name);
        this.value = name;
    }

    private void validate(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name 은 null 일 수 없습니다.");
        }
    }

    public String getValue() {
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

        DisplayedName that = (DisplayedName) o;

        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
