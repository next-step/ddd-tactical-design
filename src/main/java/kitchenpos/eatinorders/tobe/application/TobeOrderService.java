package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.dto.request.EatInOrderCreateRequest;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TobeOrderService {
    private final EatInOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final TobeOrderTableService tobeOrderTableService;

    private final MenuDomainService menuDomainService;

    public TobeOrderService(
            final EatInOrderRepository orderRepository,
            final MenuRepository menuRepository,
            final TobeOrderTableService tobeOrderTableService,
            final MenuDomainService menuDomainService
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.tobeOrderTableService = tobeOrderTableService;
        this.menuDomainService = menuDomainService;
    }

    @Transactional
    public EatInOrder create(final EatInOrderCreateRequest request) {
        final List<EatInOrderCreateRequest.OrderLineItemRequest> orderLineItemRequests = request.orderLineItemRequests();
        UUID orderTableId = request.orderTableId();

        List<EatInOrderLineItem> eatInOrderLineItems = orderLineItemRequests.stream()
                .map(item -> menuDomainService.fetchOrderLineItem(item.menuId(), item.quantity()))
                .toList();

        OrderLineItems orderLineItems = OrderLineItems.of(eatInOrderLineItems);

        tobeOrderTableService.isAvailableTable(orderTableId);

        EatInOrder eatInOrder = EatInOrder.create(orderTableId, orderLineItems);

        return orderRepository.save(eatInOrder);
    }

}
