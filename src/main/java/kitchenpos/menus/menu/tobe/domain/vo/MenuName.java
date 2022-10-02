package kitchenpos.menus.menu.tobe.domain.vo;

import kitchenpos.menus.menu.tobe.domain.Profanity;
import kitchenpos.menus.menu.tobe.domain.vo.exception.InvalidMenuNameException;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuName {

    @Column(name = "name", nullable = false)
    private String value;

    protected MenuName() {
    }

    private MenuName(final String value) {
        this.value = value;
    }

    public static MenuName valueOf(final String value, final Profanity profanity) {
        if (isNullOrEmpty(value) || profanity.containsProfanity(value)) {
            throw new InvalidMenuNameException();
        }
        return new MenuName(value);
    }

    private static boolean isNullOrEmpty(final String value) {
        return !StringUtils.hasText(value);
    }

    public String value() {
        return value;
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
        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
