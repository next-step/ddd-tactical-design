package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class MenuDisplay {
    @Column(name = "displayed", nullable = false)
    private Boolean value;

    protected MenuDisplay() {
    }

    public static MenuDisplay create(Boolean displayed) {
        if (isNull(displayed)) {
            throw new IllegalArgumentException("메뉴 숨김 여부는 비어있을 수 없습니다.");
        }

        MenuDisplay menuName = new MenuDisplay();
        menuName.value = displayed;
        return menuName;
    }

    protected Boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDisplay that = (MenuDisplay) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
