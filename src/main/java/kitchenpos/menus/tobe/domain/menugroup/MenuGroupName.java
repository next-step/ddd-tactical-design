package kitchenpos.menus.tobe.domain.menugroup;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {
    @Column(name = "name", nullable = false)
    private String menuGroupName;

    public String getMenuGroupName() {
        return menuGroupName;
    }

    public MenuGroupName(String menuGroupName) {
        validateMenuGroupName(menuGroupName);
        this.menuGroupName = menuGroupName;
    }

    protected MenuGroupName() {
    }

    private void validateMenuGroupName(String menuGroupName) {
        if (Objects.isNull(menuGroupName) || menuGroupName.isEmpty()) {
            throw new IllegalArgumentException("메뉴 그룹명이 존재하지 않습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName that = (MenuGroupName) o;
        return Objects.equals(menuGroupName, that.menuGroupName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuGroupName);
    }
}
