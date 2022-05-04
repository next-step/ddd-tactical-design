package kitchenpos.eatinorders.tobe.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.springframework.util.CollectionUtils;

@Embeddable
public class OrderLineItems {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "order_id",
        nullable = false,
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_order_line_item_to_orders")
    )
    private List<OrderLineItem> items;

    protected OrderLineItems() { }

    public OrderLineItems(final List<OrderLineItem> items) {
        if (CollectionUtils.isEmpty(items)) {
            throw new IllegalArgumentException("주문 메뉴는 반드시 1개 이상 포함되어야 합니다.");
        }

        this.items = Collections.unmodifiableList(new ArrayList<>(items));
    }

    public long sumOfMenuPrices() {
        return items.stream()
                    .mapToLong(OrderLineItem::getOrderLinePrice)
                    .sum();
    }

    public boolean hasNegativeQuantity() {
        return items.stream().anyMatch(item -> item.getQuantity() < 0);
    }

    public List<OrderLineItem> getItems() {
        return items;
    }
}
