package kitchenpos.support.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import kitchenpos.eatinorders.dto.OrderLineItemCreateRequest;
import kitchenpos.eatinorders.exception.KitchenPosIllegalArgumentException;
import kitchenpos.eatinorders.todo.domain.orders.EatInOrderOrderLineItemsPolicy;

import java.util.List;
import java.util.Objects;

import static kitchenpos.eatinorders.exception.KitchenPosExceptionMessage.INVALID_ORDER_LINE_ITEMS_SIZE;

@Embeddable
public class OrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderLineItem> values;

    protected OrderLineItems() {
    }


    public OrderLineItems(List<OrderLineItem> values) {
        this.values = values;
    }

    public static OrderLineItems of(List<OrderLineItemCreateRequest> requests, MenuClient menuClient) {
        checkNullOrEmptyOrderLineItems(requests);
        List<OrderLineItem> orderLineItems = toOrderLineItems(requests);
        validateOrderLineItems(orderLineItems, menuClient);
        return new OrderLineItems(mapping(orderLineItems, menuClient));
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

    private static void checkNullOrEmptyOrderLineItems(List<OrderLineItemCreateRequest> values) {
        if (Objects.isNull(values) || values.isEmpty()) {
            throw new KitchenPosIllegalArgumentException(INVALID_ORDER_LINE_ITEMS_SIZE);
        }
    }

    private static void validateOrderLineItems(List<OrderLineItem> values, MenuClient menuClient) {
        new EatInOrderOrderLineItemsPolicy(menuClient).checkDuplicatedMenu(values);
    }


    private static List<OrderLineItem> mapping(List<OrderLineItem> orderLineItems, MenuClient menuClient) {
        return orderLineItems.stream()
                .map(orderLineItem -> new OrderLineItem(orderLineItem, menuClient))
                .toList();
    }

    private static List<OrderLineItem> toOrderLineItems(List<OrderLineItemCreateRequest> values) {
        return values.stream()
                .map(OrderLineItemCreateRequest::toEntity)
                .toList();
    }
}
