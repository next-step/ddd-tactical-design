package kitchenpos.menus.domain.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.Embeddable;

@Embeddable
public class MenuGroupName {
    private static final String NAME_MUST_NOT_BE_EMPTY = "이름은 빈 값이 아니어야 합니다. 입력 값 : %s";

    private final String name;

    protected MenuGroupName() {
        name = null;
    }

    protected MenuGroupName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException(String.format(NAME_MUST_NOT_BE_EMPTY, name));
        }
    }
}
