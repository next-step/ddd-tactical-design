package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private String name;

    public Name() {
    }

    public Name(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Name name1 = (Name) o;

        return name.equals(name1.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
