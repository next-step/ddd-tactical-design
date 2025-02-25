package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Embeddable;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupNameException;

import java.util.Objects;

@Embeddable
public class MenuGroupName {
    private String name;

    protected MenuGroupName() {
    }

    public MenuGroupName(final String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidMenuGroupNameException("메뉴 그룹명은 공백으로만 구성될 수 없으며, 반드시 입력되어야 합니다");
        }
    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
