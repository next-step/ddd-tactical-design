package kitchenpos.order.delivery.domain.service;

import java.math.BigDecimal;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.order.common.application.MenuContextProvider;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.delivery.domain.entity.DeliveryOrder;
import kitchenpos.order.delivery.domain.repository.DeliveryOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultDeliveryService implements DeliveryService {

    private final DeliveryOrderRepository deliveryRepository;
    private final DeliveryKitchenridersClient deliveryKitchenridersClient;
    private final MenuContextProvider menuContextProvider;

    public DefaultDeliveryService(
        DeliveryOrderRepository deliveryRepository,
        DeliveryKitchenridersClient deliveryKitchenridersClient,
        MenuContextProvider menuContextProvider
    ) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryKitchenridersClient = deliveryKitchenridersClient;
        this.menuContextProvider = menuContextProvider;
    }

    @Transactional
    public OrderVo.OrderInfo startDelivery(final OrderId orderId) {
        final DeliveryOrder order = deliveryRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        order.validateStartDeliveryOrder();
        order.updateOrderStatus(OrderStatus.DELIVERING);
        return OrderVo.OrderInfo.fromEntity(order);
    }

    @Transactional
    public OrderVo.OrderInfo completeDelivery(final OrderId orderId) {
        final DeliveryOrder order = deliveryRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        order.validateCompleteDeliveryOrder();
        order.updateOrderStatus(OrderStatus.DELIVERED);
        return OrderVo.OrderInfo.fromEntity(order);
    }

    @Override
    public void requestDelivery(OrderId orderId, OrderLineItems items) {
        final DeliveryOrder order = deliveryRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        BigDecimal sum = order.getOrderLineItems().getItems().stream()
            .map(orderLineItem -> menuContextProvider.getPrice(orderLineItem.getMenuId())
                .multiply(BigDecimal.valueOf(orderLineItem.getQuantity().get())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        deliveryKitchenridersClient.requestDelivery(orderId.get(), sum, order.getDeliveryAddress());
    }
}
