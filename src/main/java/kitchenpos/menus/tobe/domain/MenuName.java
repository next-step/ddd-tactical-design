package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MenuName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(final String name, final ProfanityChecker profanityChecker) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("메뉴명은 필수로 입력해야 합니다.");
        }
        if (profanityChecker.containsProfanity(name)) {
            throw new IllegalArgumentException("메뉴명에 욕설이 포함되어 있습니다.");
        }
        this.name = name;
    }

    public String value() {
        return name;
    }
}
