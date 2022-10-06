package kitchenpos.menus.tobe.domain.model;

import kitchenpos.menus.tobe.domain.exception.ProfaneNameException;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {

    private String name;

    protected MenuGroupName() {
    }

    MenuGroupName(Profanity profanity, String name) {
        validateName(profanity, name);
        this.name = name;
    }

    private void validateName(Profanity profanity, String name) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException();
        }
        if (profanity.contains(name)) {
            throw new ProfaneNameException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
