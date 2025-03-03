package kitchenpos.order.common.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.order.common.application.MenuContextProvider;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderLineItem;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.exception.OrderHideMenuException;
import kitchenpos.order.common.domain.exception.OrderMenuInvalidException;
import kitchenpos.order.common.domain.exception.OrderPriceInvalidException;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.common.domain.model.OrderVo.Create;
import kitchenpos.order.common.domain.model.OrderVo.OrderInfo;
import kitchenpos.order.common.domain.repository.OrderRepository;
import kitchenpos.order.delivery.domain.entity.DeliveryOrder;
import kitchenpos.order.delivery.domain.service.DeliveryService;
import kitchenpos.order.eatin.domain.model.EatInOrder;
import kitchenpos.order.eatin.domain.service.EatinService;
import kitchenpos.order.takeout.domain.model.TakeOutOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DefaultOrderService implements OrderQueryService, OrderCommandService {

    private final OrderRepository orderRepository;
    private final DeliveryService deliveryService;
    private final EatinService eatinService;

    private final MenuContextProvider menuContextProvider;

    public DefaultOrderService(
        OrderRepository orderRepository,
        DeliveryService deliveryService, EatinService eatinService,
        MenuContextProvider menuContextProvider
    ) {
        this.orderRepository = orderRepository;
        this.deliveryService = deliveryService;
        this.eatinService = eatinService;
        this.menuContextProvider = menuContextProvider;
    }

    @Override
    public OrderVo.OrderInfo create(Create request) {
        final OrderId orderId = OrderId.of(UUID.randomUUID());
        OrderLineItems orderLineItems = validateOrderLineItems(orderId, request);

        Order order = switch (request.type()) {
            case EAT_IN -> EatInOrder.createEatInOrder(orderId, request, orderLineItems);
            case DELIVERY -> DeliveryOrder.createDeliveryOrder(orderId, request, orderLineItems);
            case TAKEOUT -> TakeOutOrder.createTakeoutOrder(orderId, orderLineItems);
        };

        return OrderVo.OrderInfo.fromEntity(orderRepository.save(order));
    }

    private OrderLineItems validateOrderLineItems(OrderId orderId, Create request) {
        var menuIds = request.orderLineItems().getItems().stream()
            .map(OrderLineItem::getMenuId)
            .toList();

        var menuInfos = menuContextProvider.findMenus(menuIds);

        if (menuInfos.size() != menuIds.size()) {
            throw new OrderMenuInvalidException();
        }

        var orderLineItems = request.orderLineItems().getItems().stream()
            .map(item -> {
                MenuInfo menuInfo = menuInfos.stream()
                    .filter(m -> m.id().equals(item.getMenuId()))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString()));

                validateMenu(menuInfo, item);

                return new OrderLineItem(menuInfo.id(), orderId, item.getQuantity(), item.getPrice());
            })
            .toList();

        return new OrderLineItems(orderLineItems);
    }

    private void validateMenu(MenuInfo menuInfo, OrderLineItem item) {
        if (!menuInfo.displayed()) {
            throw new OrderHideMenuException();
        }
        if (!menuInfo.price().isEqual(item.getPrice())) {
            throw new OrderPriceInvalidException();
        }
    }

    @Override
    public OrderVo.OrderInfo accept(OrderId orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        order.validateIsWaiting();

        if (order.isDelivery()) {
            deliveryService.requestDelivery(orderId, order.getOrderLineItems());
        }

        order.updateOrderStatus(OrderStatus.ACCEPTED);
        return OrderVo.OrderInfo.fromEntity(order);
    }

    @Override
    public OrderVo.OrderInfo serve(final OrderId orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        order.validateIsAccepted();
        order.updateOrderStatus(OrderStatus.SERVED);
        return OrderVo.OrderInfo.fromEntity(order);
    }

    @Override
    public OrderInfo complete(OrderId orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        order.validateOrderCompletion();
        order.updateOrderStatus(OrderStatus.COMPLETED);

        if (order.isEatIn()) {
            eatinService.complete(order.getOrderTableId());
        }

        return OrderVo.OrderInfo.fromEntity(order);
    }


    @Override
    @Transactional(readOnly = true)
    public List<OrderVo.OrderInfo> findAll() {
        return orderRepository.findAll()
            .stream()
            .map(OrderInfo::fromEntity)
            .toList();
    }
}

