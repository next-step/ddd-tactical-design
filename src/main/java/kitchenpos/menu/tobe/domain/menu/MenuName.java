package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.menu.tobe.domain.menu.validate.ProfanityValidator;

import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, ProfanityValidator profanityValidator) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException("메뉴명이 비어있습니다.");
        }
        profanityValidator.validate(name);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuName menuName = (MenuName) o;
        return Objects.equals(name, menuName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
