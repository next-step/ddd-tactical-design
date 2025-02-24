package kitchenpos.menu.tobe.domain.menugroup;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String value;


    public static MenuGroupName of(String name) {
        return new MenuGroupName(name);
    }

    private MenuGroupName(String value) {
        validate(value);
        this.value = value;
    }

    protected MenuGroupName() {}

    private void validate(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            throw new IllegalArgumentException("메뉴 그룹 이름은 비어있을 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}