package kitchenpos.eatinorder.domain.model.todo;

import java.util.List;
import java.util.Objects;

public class EatInOrderLineItems {
    private final List<EatInOrderLineItem> eatInOrderLineItems;

    private EatInOrderLineItems(final List<EatInOrderLineItem> eatInOrderLineItems) {
        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public static EatInOrderLineItems of(final List<EatInOrderLineItem> eatInOrderLineItems) {
        if (eatInOrderLineItems == null || eatInOrderLineItems.isEmpty()) {
            throw new IllegalArgumentException("식사 주문 항목은 하나 이상 포함되어야 합니다.");
        }
        return new EatInOrderLineItems(eatInOrderLineItems);
    }

    public List<EatInOrderLineItem> getEatInOrderLineItems() {
        return eatInOrderLineItems;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrderLineItems that = (EatInOrderLineItems) o;
        return Objects.equals(eatInOrderLineItems, that.eatInOrderLineItems);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eatInOrderLineItems);
    }
}
