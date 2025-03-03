package kitchenpos.order.common.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.order.common.domain.entity.OrderLineItem;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    @JsonManagedReference
    private List<OrderLineItem> items = new ArrayList<>();

    public static OrderLineItems of(final List<OrderLineItem> items) {
        if (items == null || items.isEmpty()) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_ORDER_ITEM.toString());
        }
        return new OrderLineItems(items);
    }

    protected OrderLineItems() {}

    public OrderLineItems(List<OrderLineItem> items) {
        this.items = items;
    }

    public List<OrderLineItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addItem(OrderLineItem orderLineItem) {
        this.items.add(orderLineItem);
    }

    public void removeItem(OrderLineItem orderLineItem) {
        this.items.remove(orderLineItem);
    }
}
