package kitchenpos.orders.common.domain.tobe;

import java.util.List;
import kitchenpos.menus.domain.tobe.Menu;
import org.springframework.stereotype.Component;

@Component
public class OrderLineItemsValidator {

    public void validate(List<OrderLineItem> orderLineItems, List<Menu> menus){
        if (orderLineItems == null || menus == null) {
            throw new IllegalArgumentException();
        }

        if (orderLineItems.isEmpty() || menus.isEmpty()) {
            throw new IllegalArgumentException();
        }

        if (orderLineItems.size() != menus.size()) {
            throw new IllegalArgumentException();
        }
    }
}
