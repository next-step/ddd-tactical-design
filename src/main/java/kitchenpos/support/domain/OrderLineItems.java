package kitchenpos.support.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kitchenpos.eatinorders.dto.OrderLineItemCreateRequest;

import java.util.List;
import java.util.Objects;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderLineItem> values;

    protected OrderLineItems() {
    }

    public OrderLineItems(List<OrderLineItem> values) {
        this.values = values;
    }

    public static OrderLineItems from(List<OrderLineItemCreateRequest> requests) {
        List<OrderLineItem> orderLineItems = toOrderLineItems(requests);
        return new OrderLineItems(orderLineItems);
    }

    public List<OrderLineItem> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItems that = (OrderLineItems) o;
        return Objects.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    private static List<OrderLineItem> toOrderLineItems(List<OrderLineItemCreateRequest> values) {
        return values.stream()
                .map(OrderLineItemCreateRequest::toEntity)
                .toList();
    }
}
