package kitchenpos.menus.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuNameException;

import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, Profanities profanityChecker) {
        if (name == null || name.isBlank()) {
            throw new InvalidMenuNameException("메뉴 이름이 존재해야 합니다.");
        }
        if (profanityChecker.containsProfanity(name)) {
            throw new InvalidMenuNameException("메뉴 이름에는 비속어가 포함되면 안됩니다.");
        }
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuName menuName)) return false;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
