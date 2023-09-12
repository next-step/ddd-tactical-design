package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, MenuPurgomalumClient menuPurgomalumClient) {
        validateName(name, menuPurgomalumClient);
        this.name = name;
    }

    private static void validateName(String name, MenuPurgomalumClient menuPurgomalumClient) {
        if (Objects.isNull(name) || menuPurgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuName)) return false;
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
