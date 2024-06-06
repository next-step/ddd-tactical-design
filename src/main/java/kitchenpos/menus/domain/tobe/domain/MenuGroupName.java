package kitchenpos.menus.domain.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    private MenuGroupName() {
    }

    private MenuGroupName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("메뉴 그룹의 이름은 비워 둘 수 없습니다.");
        }
        this.name = name;
    }

    public static MenuGroupName of(String name) {
        return new MenuGroupName(name);
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