package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, PurgomalumClient client) {
        if (Objects.isNull(name) || client.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
