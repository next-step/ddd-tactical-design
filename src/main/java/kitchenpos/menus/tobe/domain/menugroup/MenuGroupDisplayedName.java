package kitchenpos.menus.tobe.domain.menugroup;

import kitchenpos.common.domain.ValueObject;
import kitchenpos.menus.exception.MenuDisplayedNameException;
import kitchenpos.menus.exception.MenuErrorCode;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupDisplayedName extends ValueObject {
    private String name;

    protected MenuGroupDisplayedName() {

    }

    public MenuGroupDisplayedName(String name) {
        if (isNullAndEmpty(name)) {
            throw new MenuDisplayedNameException(MenuErrorCode.NAME_IS_NULL_OR_EMPTY);
        }
        this.name = name;
    }

    private boolean isNullAndEmpty(String name) {
        return name == null || name.isBlank();
    }

    public String getValue() {
        return name;
    }
    
}
