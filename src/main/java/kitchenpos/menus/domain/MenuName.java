package kitchenpos.menus.domain;

import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.Embeddable;
import java.util.Objects;

import static kitchenpos.menus.util.NameValidator.validateContainsProfanity;
import static kitchenpos.menus.util.NameValidator.validateNameEmpty;

@Embeddable
public class MenuName {
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, PurgomalumClient client) {
        validateNameEmpty(name);
        validateContainsProfanity(name, client);
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
