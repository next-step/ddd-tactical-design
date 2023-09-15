package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class MenuProductQuantity {
    @Column(name = "quantity", nullable = false)
    private Long value;

    protected MenuProductQuantity() {
    }

    public static MenuProductQuantity create(Long value) {
        validateMenuProductQuantityIsNull(value);
        validateMenuProductQuantityIsNegative(value);

        MenuProductQuantity menuProductQuantity = new MenuProductQuantity();
        menuProductQuantity.value = value;
        return menuProductQuantity;
    }

    private static void validateMenuProductQuantityIsNegative(Long value) {
        if (isNegative(value)) {
            throw new IllegalArgumentException("메뉴 가격은 음수일 수 없습니다.");
        }
    }

    private static void validateMenuProductQuantityIsNull(Long value) {
        if (isNull(value)) {
            throw new IllegalArgumentException("메뉴 가격은 비어있을 수 없습니다.");
        }
    }

    private static boolean isNegative(Long value) {
        return 0L > value;
    }

    Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductQuantity that = (MenuProductQuantity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}