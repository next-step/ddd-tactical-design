package kitchenpos.menus.domain.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class DisplayedName {
    private static final String MENU_NAME_MUST_NOT_BE_EMPTY = "메뉴 상품 목록은 빈 값이 아니어야 합니다. 입력 값 : %s";

    private final String name;

    protected DisplayedName() {
        this.name = null;
    }

    public DisplayedName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException(String.format(MENU_NAME_MUST_NOT_BE_EMPTY, name));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayedName that = (DisplayedName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
