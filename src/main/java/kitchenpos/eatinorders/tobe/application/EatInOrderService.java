package kitchenpos.eatinorders.tobe.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.tobe.domain.model.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.model.Order;
import kitchenpos.eatinorders.tobe.domain.model.OrderDomainService;
import kitchenpos.eatinorders.tobe.domain.model.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.model.OrderQuantity;
import kitchenpos.eatinorders.tobe.domain.repository.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.tobe.dto.OrderRequest;
import kitchenpos.eatinorders.tobe.dto.OrderResponse;
import kitchenpos.menus.tobe.menu.domain.model.Menu;
import kitchenpos.menus.tobe.menu.domain.model.MenuPrice;
import kitchenpos.menus.tobe.menu.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    public EatInOrderService(final OrderRepository orderRepository, final OrderDomainService orderDomainService, final MenuRepository menuRepository, final OrderTableRepository orderTableRepository) {
        this.orderRepository = orderRepository;
        this.orderDomainService = orderDomainService;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public OrderResponse create(final OrderRequest request) {
        request.validate();

        final List<OrderLineItemRequest> orderLineItemRequests = request.getOrderLineItems();

        final List<OrderLineItem> orderLineItems = orderLineItemRequests.stream()
            .map(this::createOrderLineItem)
            .collect(Collectors.toList());

        final List<Menu> menus = menuRepository.findAllByIdIn(
            orderLineItemRequests.stream()
                .map(OrderLineItemRequest::getMenuId)
                .collect(Collectors.toList())
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new IllegalArgumentException("메뉴가 없으면 등록할 수 없습니다.");
        }

        for (final OrderLineItemRequest orderLineItemRequest : orderLineItemRequests) {
            final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
                .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException("하나라도 숨겨진 메뉴가 있으면 주문할 수 없습니다.");
            }
        }

        final Order order = new EatInOrder(request.getOrderTableId(), orderLineItems);
        orderDomainService.validateOrder(order);
        return createOrderResponse(orderRepository.save(order));
    }

    private OrderResponse createOrderResponse(final Order order) {
        return new OrderResponse(
            order.getId(),
            order.getType(),
            order.getStatus(),
            order.getOrderDateTime(),
            order.getOrderLineItems(),
            orderDomainService.getOrderTable(order),
            order.getOrderTableId()
        );
    }

    private OrderLineItem createOrderLineItem(final OrderLineItemRequest orderLineItemRequest) {
        return new OrderLineItem(orderLineItemRequest.getMenuId(), new MenuPrice(orderLineItemRequest.getPrice()), new OrderQuantity(orderLineItemRequest.getQuantity()));
    }

}

