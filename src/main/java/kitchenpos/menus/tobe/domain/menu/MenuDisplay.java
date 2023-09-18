package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class MenuDisplay {
    @Column(name = "displayed", nullable = false)
    private Boolean value;

    private MenuDisplay() {
    }

    private MenuDisplay(Boolean value) {
        this.value = value;
    }

    public static MenuDisplay create(Boolean displayed) {
        if (isNull(displayed)) {
            throw new IllegalArgumentException("메뉴 숨김 여부는 비어있을 수 없습니다.");
        }

        return new MenuDisplay(displayed);
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
