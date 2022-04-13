package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName extends ValueObject<MenuGroupName> {

    @Column(name = "name")
    private String name;

    public MenuGroupName(String name) {
        if(Objects.isNull(name) || "".equals(name)) {
            throw new IllegalArgumentException("메뉴이름은 빈 값이 될 수 없습니다.");
        }
        this.name = name;
    }

    protected MenuGroupName() {

    }

    public String getValue() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuGroupName menuName = (MenuGroupName) o;

        return name != null ? name.equals(menuName.name) : menuName.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
