package kitchenpos.eatinorders.tobe.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    @Column(name = "name")
    private String name;

    public DisplayedName() {
    }

    public DisplayedName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름이 비어있으면 안됩니다.");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
