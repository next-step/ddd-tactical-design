package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.function.Predicate;

@Embeddable
public class MenuName {

    private String name;

    public MenuName() {
    }

    public String getName() {
        return name;
    }

    public MenuName(final String name, final Predicate<String> predicate) {
        if (Objects.isNull(name) || predicate.test(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuName productName1 = (MenuName) o;

        return name.equals(productName1.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
