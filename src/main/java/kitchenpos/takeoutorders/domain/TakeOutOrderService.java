package kitchenpos.takeoutorders.domain;

import kitchenpos.eatinorders.domain.Order;
import kitchenpos.eatinorders.domain.OrderType;
import kitchenpos.eatinorders.domain.strategy.OrderProcess;
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
        throw new IllegalStateException("배달 주문은 배달 주문만 가능합니다.");
    }

    @Override
    public Order completeDelivery(Order order) {
        throw new IllegalStateException("배달 주문은 배달 주문만 가능합니다.");
    }

    @Override
    public boolean support(OrderType orderType) {
        return OrderType.TAKEOUT.equals(orderType);
    }
}
