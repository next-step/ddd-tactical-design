package kitchenpos.apply.order.eatinorders.tobe.application;

import kitchenpos.apply.menus.tobe.domain.Menu;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import kitchenpos.apply.order.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.apply.order.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.apply.order.eatinorders.tobe.ui.dto.OrderLineItemRequest;
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
        if (orderLineItemRequests == null || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException("주문 내역은 비어있 을 수 없습니다");
        }

        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItemRequest::getMenuId)
                        .collect(Collectors.toList())
        );

        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException("주문내역의 메뉴는 현재 판매중인 메뉴이어야 한다");
        }

        orderLineItemRequests.stream()
                .filter(it -> !menuRepository.existsByIdAndDisplayedWithPrice(it.getMenuId(), it.getPrice()))
                .findAny()
                .ifPresent(it -> {
                    throw new NoSuchElementException("활성화된 메뉴를 찾지 못하였습니다 ID: " + it.getMenuId() + " Price: " + it.getPrice());
                });

        List<OrderLineItem> orderLineItems = orderLineItemRequests.stream()
                .map(it -> OrderLineItem.of(it.getMenuId(), it.getQuantity(), it.getPrice()))
                .collect(Collectors.toList());

        return new OrderLineItems(orderLineItems);
    }

}
