package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.ui.dto.OrderLineItemRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class OrderLineItemsFactory {

    private final MenuRepository menuRepository;

    public OrderLineItemsFactory(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public OrderLineItems create(List<OrderLineItemRequest> orderLineItemRequests) {
        if (orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItemRequest::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }

        List<OrderLineItem> orderLineItems = orderLineItemRequests.stream()
                .map(it -> {
                    final Menu menu = menuRepository.findByIdAndDisplayedWithPrice(it.getMenuId(), it.getPrice())
                            .orElseThrow(NoSuchElementException::new);
                    return OrderLineItem.of(menu, it.getQuantity(), it.getPrice());
                })
                .collect(Collectors.toList());

        return new OrderLineItems(orderLineItems);
    }

}
