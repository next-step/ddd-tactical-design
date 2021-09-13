package kitchenpos.menus.domain.tobe.domain.menu;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {

    @Column(name = "quantity", nullable = false)
    private Long value;

    protected MenuProductQuantity() {
    }

    public MenuProductQuantity(final Long value) {
        validate(value);
        this.value = value;
    }

    private void validate(final Long value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 수량이 올바르지 않으면 등록할 수 없습니다.");
        }

        if (value < 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품의 수량은 0 이상이어야 합니다.");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MenuProductQuantity that = (MenuProductQuantity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
