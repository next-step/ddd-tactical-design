package kitchenpos.order.takeoutorders.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.supports.strategy.OrderProcess;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderAcceptService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderCompleteService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderCreateService;
import kitchenpos.order.takeoutorders.domain.TakeOutOrderServeService;
import org.springframework.stereotype.Component;

@Component
public class TakeOutOrderService implements OrderProcess {


    private final TakeOutOrderCreateService takeOutOrderCreateService;
    private final TakeOutOrderAcceptService takeOutOrderAcceptService;
    private final TakeOutOrderServeService takeOutOrderServeService;
    private final TakeOutOrderCompleteService takeOutOrderCompleteService;

    public TakeOutOrderService(TakeOutOrderCreateService takeOutOrderCreateService, TakeOutOrderAcceptService takeOutOrderAcceptService, TakeOutOrderServeService takeOutOrderServeService, TakeOutOrderCompleteService takeOutOrderCompleteService) {
        this.takeOutOrderCreateService = takeOutOrderCreateService;
        this.takeOutOrderAcceptService = takeOutOrderAcceptService;
        this.takeOutOrderServeService = takeOutOrderServeService;
        this.takeOutOrderCompleteService = takeOutOrderCompleteService;
    }

    @Override
    public Order create(Order order) {
        return this.takeOutOrderCreateService.create(order);
    }

    @Override
    public Order accept(Order order) {
        return this.takeOutOrderAcceptService.accept(order);
    }

    @Override
    public Order serve(Order order) {
        return this.takeOutOrderServeService.serve(order);
    }

    @Override
    public Order complete(Order order) {
        return this.takeOutOrderCompleteService.complete(order);
    }

    @Override
    public Order startDelivery(Order order) {
        throw new IllegalStateException("배달 주문이 아닙니다.");
    }

    @Override
    public Order completeDelivery(Order order) {
        throw new IllegalStateException("배달 주문이 아닙니다.");
    }

    @Override
    public boolean support(OrderType orderType) {
        return orderType.equals(OrderType.TAKEOUT);
    }
}
