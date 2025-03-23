package kitchenpos.eatinorders.tobe.domain.order.vo;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import static java.util.Objects.isNull;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class EatInOrderLineItems {

    @Transient
    private List<EatInOrderLineItem> items;

    protected EatInOrderLineItems() {
        this.items = new ArrayList<>();
    }

    public EatInOrderLineItems(final List<EatInOrderLineItem> items) {
        if (isNull(items) || items.isEmpty()) {
            throw new IllegalArgumentException("주문 항목이 존재해야 합니다.");
        }
        this.items = new ArrayList<>(items);
    }

    public int totalPrice() {
        return items.stream()
                .mapToInt(EatInOrderLineItem::amount)
                .sum();
    }
}
