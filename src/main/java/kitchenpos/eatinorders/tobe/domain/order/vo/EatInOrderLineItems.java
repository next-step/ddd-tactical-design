package kitchenpos.eatinorders.tobe.domain.order.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import static java.util.Objects.isNull;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidEatInOrderLineItemException;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderMenus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Embeddable
public class EatInOrderLineItems {

    @Transient
    private List<EatInOrderLineItem> items;

    protected EatInOrderLineItems() {
        this.items = new ArrayList<>();
    }

    public EatInOrderLineItems(final List<EatInOrderLineItem> items) {
        if (isNull(items) || items.isEmpty()) {
            throw new InvalidEatInOrderLineItemException("주문 항목이 존재해야 합니다.");
        }
        this.items = new ArrayList<>(items);
    }

    public void verifyMenus(final EatInOrderMenus menus) {
        if (!menus.isSameSize(items.size())) {
            throw new InvalidEatInOrderLineItemException("주문 항목 수와 메뉴 수가 일치하지 않습니다.");
        }

        for (EatInOrderLineItem item : items) {
            if (!menus.isDisplayed(item.getMenuId())) {
                throw new InvalidEatInOrderLineItemException("표시되지 않은 메뉴는 주문할 수 없습니다.");
            }

            if (!menus.isSamePrice(item.getMenuId(), item.getPrice())) {
                throw new InvalidEatInOrderLineItemException("주문 항목에 있는 가격이 메뉴에 있는 가격과 동일하지 않습니다.");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EatInOrderLineItems that)) return false;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
