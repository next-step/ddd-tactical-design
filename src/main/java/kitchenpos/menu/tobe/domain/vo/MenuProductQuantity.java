package kitchenpos.menu.tobe.domain.vo;

/**
 * <h1>메뉴 상품 수량</h1>
 * <ul>
 *     <li>메뉴 상품별 수량은 1 이상이다.</li>
 * </ul>
 */
public class MenuProductQuantity {

    public final long value;

    public MenuProductQuantity(final long value) {
        if (value < 1) {
            throw new IllegalArgumentException("MenuProductQuantity는 1보다 작을 수 없습니다");
        }
        this.value = value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MenuProductQuantity that = (MenuProductQuantity) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}
