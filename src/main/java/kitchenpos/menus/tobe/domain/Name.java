package kitchenpos.menus.tobe.domain;

import kitchenpos.common.Profanities;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    private String name;

    protected Name() {

    }

    public Name(String name, Profanities profanities) {
        if (name == null || profanities.contains(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name name1 = (Name) o;
        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
