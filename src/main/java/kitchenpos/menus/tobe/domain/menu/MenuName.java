package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.domain.PurgomalumClient;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String menuName;

    public String getMenuName() {
        return menuName;
    }

    public MenuName(String menuName, PurgomalumClient purgomalumClient) {
        validateMenuName(menuName, purgomalumClient);
        this.menuName = menuName;
    }

    private void validateMenuName(String menuName, PurgomalumClient purgomalumClient) {
        if (Objects.isNull(menuName) || purgomalumClient.containsProfanity(menuName)) {
            throw new IllegalArgumentException();
        }
    }

    protected MenuName() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName menuName1 = (MenuName) o;
        return Objects.equals(menuName, menuName1.menuName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuName);
    }
}
