package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    private String name;

    protected DisplayedName() {

    }

    public DisplayedName(String name) {
        this.name = name;
    }


    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
