package kitchenpos.eatinorders.tobe.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

@Embeddable
public class OrderLineItems {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderLineItem> orderLineItems;

    protected OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> orderLineItems) {
        if (orderLineItems == null || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException("주문할 메뉴가 존재해야 합니다.");
        }
        this.orderLineItems = orderLineItems;
    }

    public void changeQuantity(Long orderLineItemId, int count) {
        OrderLineItem orderLineItem = findById(orderLineItemId);
        orderLineItem.changeQuantity(count);
    }

    private OrderLineItem findById(Long orderLineItemId) {
        return orderLineItems.stream()
                .filter(orderLineItem -> orderLineItem.equalsId(orderLineItemId))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("주문 메뉴가 존재하지 않습니다."));
    }

    public List<OrderLineItem> getOrderLineItems() {
        return Collections.unmodifiableList(orderLineItems);
    }
}
