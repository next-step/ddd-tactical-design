package kitchenpos.eatinorders.tobe.domain.service;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.tobe.domain.Order;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.menu.MenuManager;
import kitchenpos.eatinorders.tobe.exception.IllegalDeliverException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static kitchenpos.eatinorders.tobe.domain.OrderStatus.WAITING;
import static kitchenpos.eatinorders.tobe.domain.OrderType.DELIVERY;

@Component
public class OrderDeliverService {
    private final MenuManager menuManager;
    private final KitchenridersClient kitchenridersClient;

    public OrderDeliverService(final MenuManager menuManager, final KitchenridersClient kitchenridersClient) {
        this.menuManager = menuManager;
        this.kitchenridersClient = kitchenridersClient;
    }

    public void deliver(final Order order) {
        if (order.getStatus() != WAITING) {
            throw new IllegalDeliverException("접수 대기 중인 주문은 접수 할 수 없습니다.");
        }
        if (order.getType() == DELIVERY) {
            final BigDecimal sum = order.getOrderLineItems().stream()
                    .map(this::calculateTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            kitchenridersClient.requestDelivery(order.getId(), sum, order.getDeliveryAddress());
        }
    }

    private BigDecimal calculateTotalPrice(final OrderLineItem orderLineItem) {
        return menuManager.getMenu(orderLineItem.getMenuId()).getPrice()
                .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
    }
}
