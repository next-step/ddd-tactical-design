package kitchenpos.menus.tobe.domain.menu;

import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

//TODO DisplayedName 클래스 재사용
public class MenuName {

    private final String name;
    private final boolean isProfanity;

    public MenuName(String name, boolean isProfanity) {
        validate(name, isProfanity);
        this.name = name;
        this.isProfanity = isProfanity;
    }

    private void validate(String name, boolean isProfanity) {
        if (Strings.isEmpty(name) || Objects.isNull(name)) {
            throw new IllegalArgumentException("메뉴명은 null 이나 공백일 수 없습니다.");
        }
        if (isProfanity) {
            throw new IllegalArgumentException("메뉴명에 비속어를 포함할 수 없습니다.");
        }
    }
}
