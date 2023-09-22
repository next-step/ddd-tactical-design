package kitchenpos.order.deliveryorders.application;

import kitchenpos.order.deliveryorders.domain.*;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.supports.strategy.OrderProcess;
import org.springframework.stereotype.Component;

@Component
public class DeliveryOrderService implements OrderProcess {

    private final DeliveryOrderCreateService deliveryOrderCreateService;
    private final DeliveryOrderAcceptService deliveryOrderAcceptService;
    private final DeliveryOrderServeService deliveryOrderServeService;
    private final DeliveryOrderCompleteService deliveryOrderCompleteService;
    private final DeliveryOrderStartDeliveryService deliveryOrderStartDeliveryService;
    private final DeliveryOrderCompleteDeliveryService deliveryOrderCompleteDeliveryService;

    public DeliveryOrderService(DeliveryOrderCreateService deliveryOrderCreateService, DeliveryOrderAcceptService deliveryOrderAcceptService, DeliveryOrderServeService deliveryOrderServeService, DeliveryOrderCompleteService deliveryOrderCompleteService, DeliveryOrderStartDeliveryService deliveryOrderStartDeliveryService, DeliveryOrderCompleteDeliveryService deliveryOrderCompleteDeliveryService) {
        this.deliveryOrderCreateService = deliveryOrderCreateService;
        this.deliveryOrderAcceptService = deliveryOrderAcceptService;
        this.deliveryOrderServeService = deliveryOrderServeService;
        this.deliveryOrderCompleteService = deliveryOrderCompleteService;
        this.deliveryOrderStartDeliveryService = deliveryOrderStartDeliveryService;
        this.deliveryOrderCompleteDeliveryService = deliveryOrderCompleteDeliveryService;
    }

    @Override
    public Order create(Order order) {
        return this.deliveryOrderCreateService.create(order);
    }

    @Override
    public Order accept(Order order) {
        return this.deliveryOrderAcceptService.accept(order);
    }

    @Override
    public Order serve(Order order) {
        return this.deliveryOrderServeService.serve(order);
    }

    @Override
    public Order complete(Order order) {
        return this.deliveryOrderCompleteService.complete(order);
    }

    @Override
    public Order startDelivery(Order order) {
        return this.deliveryOrderStartDeliveryService.startDelivery(order);
    }

    @Override
    public Order completeDelivery(Order order) {
        return this.deliveryOrderCompleteDeliveryService.completeDelivery(order);
    }

    @Override
    public boolean support(OrderType orderType) {
        return OrderType.DELIVERY.equals(orderType);
    }
}
