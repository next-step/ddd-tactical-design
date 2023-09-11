package kitchenpos.menus.tobe.domain;

import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuException;
import kitchenpos.products.infra.PurgomalumClient;

@Embeddable
public class MenuName {

    String name;

    protected MenuName() {
    }

    public MenuName(String name, PurgomalumClient purgomalumClient) {
        if (isNullOrEmpty(name)) {
            throw new MenuException(MenuErrorCode.NAME_IS_NOT_EMPTY_OR_NULL);
        }
        if (purgomalumClient.containsProfanity(name)) {
            throw new MenuException(MenuErrorCode.NAME_CONTAINS_PROFANITY);
        }
        this.name = name;
    }

    private boolean isNullOrEmpty(String name) {
        return name == null || name.isEmpty();
    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
