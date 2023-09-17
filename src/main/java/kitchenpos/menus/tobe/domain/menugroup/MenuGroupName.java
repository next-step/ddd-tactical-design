package kitchenpos.menus.tobe.domain.menugroup;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String value;

    protected MenuGroupName() {
    }

    protected MenuGroupName(String value) {
        this.value = value;
    }

    public static MenuGroupName create(String value) {
        if (isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException("메뉴 그룹명은 비어있을 수 없습니다.");
        }

        return new MenuGroupName(value);
    }

    protected String getValue() {
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
}
