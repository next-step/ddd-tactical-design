package kitchenpos.eatinorders.feedback.domain;

import java.util.List;

@FunctionalInterface
public interface OrderCreatePolicy {
    void validate(List<OrderLineItem> orderLineItems, Long orderTableId);
}
