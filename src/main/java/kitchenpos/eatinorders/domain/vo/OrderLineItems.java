package kitchenpos.eatinorders.domain.vo;

import kitchenpos.eatinorders.domain.EatInOrderLineItem;
import kitchenpos.menus.domain.Menu;
import kitchenpos.support.ValueObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Embeddable
public class OrderLineItems extends ValueObject {
    private List<EatInOrderLineItem> eatInOrderLineItems;

    public OrderLineItems() {
    }

    public OrderLineItems(List<EatInOrderLineItem> eatInOrderLineItems) {
        if (Objects.isNull(eatInOrderLineItems) || eatInOrderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.eatInOrderLineItems = eatInOrderLineItems;
    }

    public BigDecimal sum() {
        return this.eatInOrderLineItems.stream()
                .map(EatInOrderLineItem::multiply)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<EatInOrderLineItem> getOrderLineItems() {
        return eatInOrderLineItems;
    }
}
