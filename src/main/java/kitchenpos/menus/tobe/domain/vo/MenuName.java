package kitchenpos.menus.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuNameException;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, ProfanityClient profanityChecker) {
        if (name == null || name.isBlank()) {
            throw new InvalidMenuNameException("메뉴 이름이 존재해야 합니다.");
        }
        if (profanityChecker.containsProfanity(name)) {
            throw new InvalidMenuNameException("메뉴 이름에는 비속어가 포함되면 안됩니다.");
        }
        this.name = name;
    }
}
