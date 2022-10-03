package kitchenpos.menus.tobe.domain.menugroup;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuGroupName {

    @Column(name = "name", nullable = false)
    private String name;

    protected MenuGroupName() {}

    public MenuGroupName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("상품명은 null 이나 공백일 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuGroupName that = (MenuGroupName) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
