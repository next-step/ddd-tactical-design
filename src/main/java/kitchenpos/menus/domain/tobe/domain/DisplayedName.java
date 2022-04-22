package kitchenpos.menus.domain.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DisplayedName {
    private static final String MENU_NAME_MUST_NOT_BE_EMPTY = "메뉴 이름은 빈 값이 아니어야 합니다. 입력 값 : %s";

    @Column(name = "name", nullable = false)
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
}
