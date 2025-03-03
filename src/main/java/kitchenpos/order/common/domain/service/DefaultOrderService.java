package kitchenpos.order.common.domain.service;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.common.domain.model.OrderVo.OrderInfo;
import kitchenpos.order.common.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DefaultOrderService implements OrderQueryService, OrderCommandService {

    private final OrderRepository orderRepository;

    public DefaultOrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderVo.OrderInfo accept(OrderId orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        order.validateWaiting();

        if (order.getType() == OrderType.DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;

            // TODO: step3 보완
//            for (final OrderLineItem orderLineItem : order.getOrderLineItems()) {
//                sum = orderLineItem.getMenu()
//                    .getPrice()
//                    .price()
//                    .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
//            }
//            deliveryKitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
        }
        order.updateOrderStatus(OrderStatus.ACCEPTED);
        return OrderVo.OrderInfo.fromEntity(order);
    }

    @Override
    public OrderVo.OrderInfo serve(final OrderId orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));

        order.validateAccepted();
        order.updateOrderStatus(OrderStatus.SERVED);
        return OrderVo.OrderInfo.fromEntity(order);
    }

    @Override
    public OrderInfo complete(OrderId orderId) {
        final Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_ORDER.toString()));
        final OrderType type = order.getType();
        final OrderStatus status = order.getStatus();
        if (type == OrderType.DELIVERY) {
            if (status != OrderStatus.DELIVERED) {
                throw new IllegalStateException();
            }
        }
        if (type == OrderType.TAKEOUT || type == OrderType.EAT_IN) {
            if (status != OrderStatus.SERVED) {
                throw new IllegalStateException();
            }
        }
//        order.updateOrderStatus(OrderStatus.COMPLETED);
//        if (type == OrderType.EAT_IN) {
//            final OrderTableId orderTable = order.getOrderTable();
//            if (!orderRepository.existsByOrderTableAndStatusNot(orderTable,
//                OrderStatus.COMPLETED)) {
//                orderTable.setNumberOfGuests(0);
//                orderTable.setOccupied(false);
//            }
//        }

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

