package kitchenpos.eatinorders.tobe.domain.order.menu;

import java.util.Objects;
import java.util.UUID;

/**
 * Menu Bounded Context 에서 조회한 Menu 정보
 * 질문 : Aggregate 로 판단? Data 로 판단? Value Object 로 판단?
 * 질문 이유 :
 * 1. Menu 이긴 하지만, EatInOrder 생성시에 불변식 기준으로 제공하고 있음
 *   1-1. EatInOrder 에서 바라보는 Menu 는 EatInOrderItem 으로 대응 중
 *   1-2. hash code, equals, toString 구현 기준 고민
 */
public class DefaultEatInOrderMenu implements EatInOrderMenu {
    private final UUID menuId;
    private final int price;
    private final boolean displayed;

    public DefaultEatInOrderMenu(final UUID menuId, final int price,  final boolean displayed) {
        this.menuId = menuId;
        this.price = price;
        this.displayed = displayed;
    }

    public boolean isSameMenu(final UUID uuid) {
        return menuId.equals(uuid);
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public boolean isSamePrice(final int price) {
        return this.price == price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final DefaultEatInOrderMenu that = (DefaultEatInOrderMenu) o;
        return Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(menuId);
    }
}
