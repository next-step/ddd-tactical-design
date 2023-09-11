package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.exception.MenuDisplayedNameException;
import kitchenpos.menus.exception.MenuErrorCode;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuDisplayedName {
    private String name;

    protected MenuDisplayedName() {

    }

    public MenuDisplayedName(String name, ProfanityPolicy profanityPolicy) {
        validate(name, profanityPolicy);
        this.name = name;
    }

    private void validate(String text, ProfanityPolicy profanityPolicy) {
        if (isNullAndEmpty(text)) {
            throw new MenuDisplayedNameException(MenuErrorCode.NAME_IS_NULL_OR_EMPTY);
        }

        if (profanityPolicy.containsProfanity(text)) {
            throw new MenuDisplayedNameException(MenuErrorCode.NAME_HAS_PROFANITY);
        }
    }

    private boolean isNullAndEmpty(String name) {
        return name == null || name.isBlank();
    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDisplayedName that = (MenuDisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
