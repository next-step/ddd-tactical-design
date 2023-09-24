package kitchenpos.order.deliveryorders.application;

import kitchenpos.order.deliveryorders.domain.*;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.supports.strategy.OrderProcess;
import org.springframework.stereotype.Service;

@Service
public class DeliveryOrderService implements OrderProcess {

    private final DeliveryOrderCreateService deliveryOrderCreateService;
    private final DeliveryOrderAcceptService deliveryOrderAcceptService;
    private final DeliveryOrderServeService deliveryOrderServeService;
    private final DeliveryOrderStartDeliveryService deliveryOrderStartDeliveryService;
    private final DeliveryOrderCompleteDeliveryService deliveryOrderCompleteDeliveryService;
    private final DeliveryOrderCompleteService deliveryOrderCompleteService;

    public DeliveryOrderService(DeliveryOrderCreateService deliveryOrderCreateService, DeliveryOrderAcceptService deliveryOrderAcceptService, DeliveryOrderServeService deliveryOrderServeService, DeliveryOrderStartDeliveryService deliveryOrderStartDeliveryService, DeliveryOrderCompleteDeliveryService deliveryOrderCompleteDeliveryService, DeliveryOrderCompleteService deliveryOrderCompleteService) {
        this.deliveryOrderCreateService = deliveryOrderCreateService;
        this.deliveryOrderAcceptService = deliveryOrderAcceptService;
        this.deliveryOrderServeService = deliveryOrderServeService;
        this.deliveryOrderStartDeliveryService = deliveryOrderStartDeliveryService;
        this.deliveryOrderCompleteDeliveryService = deliveryOrderCompleteDeliveryService;
        this.deliveryOrderCompleteService = deliveryOrderCompleteService;
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
        return orderType.equals(OrderType.DELIVERY);
    }
}
