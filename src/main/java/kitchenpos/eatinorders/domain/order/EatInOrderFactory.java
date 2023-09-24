package kitchenpos.eatinorders.domain.order;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kitchenpos.eatinorders.domain.order.vo.MenuVo;
import kitchenpos.eatinorders.domain.order.vo.OrderLineItemVo;
import kitchenpos.eatinorders.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;
import kitchenpos.menus.domain.Price;

@Component
public class EatInOrderFactory {
    private final OrderTableRepository orderTableRepository;
    private final MenuApiRepository menuApiRepository;

    public EatInOrderFactory(
            final OrderTableRepository orderTableRepository,
            final MenuApiRepository menuApiRepository) {
        this.orderTableRepository = orderTableRepository;
        this.menuApiRepository = menuApiRepository;
    }

    public EatInOrder create(UUID orderTableId, List<OrderLineItemVo> orderLineItemVos) {
        List<OrderLineItem> orderLineItems = orderLineItemVos.stream()
                .map(this::createOrderLineItem)
                .collect(Collectors.toList());

        final OrderTable orderTable = orderTableRepository.findById(orderTableId)
                .orElseThrow(NoSuchElementException::new);

        return new EatInOrder(UUID.randomUUID(), new OrderLineItems(orderLineItems), orderTable);
    }

    private OrderLineItem createOrderLineItem(OrderLineItemVo orderLineItemVo) {
        final MenuVo menuVo = menuApiRepository.findById(orderLineItemVo.getMenuId());
        if (!menuVo.isDisplayed()) {
            throw new IllegalStateException();
        }
        Price menuPrice = new Price(menuVo.getPrice());
        Price orderLineItemPrice = new Price(orderLineItemVo.getPrice());
        if (menuPrice.isNotSame(orderLineItemPrice)) {
            throw new IllegalArgumentException();
        }
        return new OrderLineItem(orderLineItemVo.getMenuId(), orderLineItemVo.getQuantity(), orderLineItemVo.getPrice());
    }
}
