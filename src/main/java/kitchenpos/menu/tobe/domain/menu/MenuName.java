package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.common.tobe.Profanities;

import java.util.Objects;

@Embeddable
public class MenuName {
    @Column(name = "name", nullable = false)
    private String name;

    protected MenuName() {
    }

    public MenuName(String name, Profanities profanities) {
        validate(name, profanities);
        this.name = name;
    }

    private void validate(String value, Profanities profanities) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("메뉴 이름은 필수입니다.");
        }
        if (profanities.contains(value)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다.");
        }
    }

    public String getName() {
        return name;
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
        return Objects.hashCode(name);
    }
}
