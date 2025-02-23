package kitchenpos.order.eatinorder.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFactory;
import kitchenpos.order.eatinorder.domain.model.EatInOrderStatus;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final EatInOrderFactory eatInOrderFactory;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final MenuRepository menuRepository,
            final EatInOrderFactory eatInOrderFactory
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.eatInOrderFactory = eatInOrderFactory;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        final List<OrderLineItem> orderLineItems = toOrderLineItems(request.getOrderLineItems());
        EatInOrder eatInOrder = eatInOrderFactory.create(orderLineItems, request.getOrderTableId());
        return eatInOrderRepository.save(eatInOrder);
    }

    private List<OrderLineItem> toOrderLineItems(List<OrderLineItem> request) {
        final List<OrderLineItem> orderLineItems = new ArrayList<>();
        for (final OrderLineItem itemRq : request) {
            final Menu menu = menuRepository.findById(itemRq.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            final OrderLineItem orderLineItem = new OrderLineItem(menu, itemRq.getQuantity(), menu.getId(),
                    itemRq.getPrice());
            orderLineItems.add(orderLineItem);
        }
        return orderLineItems;
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.processOrderFlow(EatInOrderStatus.ACCEPTED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.processOrderFlow(EatInOrderStatus.SERVED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.processOrderFlow(EatInOrderStatus.COMPLETED);

        return eatInOrder;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }
}
