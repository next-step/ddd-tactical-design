package kitchenpos.order.eatinorders.application;

import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderType;
import kitchenpos.order.eatinorders.domain.EatInOrderAcceptService;
import kitchenpos.order.eatinorders.domain.EatInOrderCompleteService;
import kitchenpos.order.eatinorders.domain.EatInOrderCreateService;
import kitchenpos.order.eatinorders.domain.EatInOrderServeService;
import kitchenpos.order.supports.strategy.OrderProcess;
import org.springframework.stereotype.Component;

@Component
public class EatInOrderService implements OrderProcess {

    private final EatInOrderCreateService eatInOrderCreateService;
    private final EatInOrderAcceptService eatInOrderAcceptService;
    private final EatInOrderServeService eatInOrderServeService;
    private final EatInOrderCompleteService eatInOrderCompleteService;

    public EatInOrderService(EatInOrderCreateService eatInOrderCreateService, EatInOrderAcceptService eatInOrderAcceptService, EatInOrderServeService eatInOrderServeService, EatInOrderCompleteService eatInOrderCompleteService) {
        this.eatInOrderCreateService = eatInOrderCreateService;
        this.eatInOrderAcceptService = eatInOrderAcceptService;
        this.eatInOrderServeService = eatInOrderServeService;
        this.eatInOrderCompleteService = eatInOrderCompleteService;
    }

    @Override
    public Order create(Order order) {
        return this.eatInOrderCreateService.create(order);
    }

    @Override
    public Order accept(Order order) {
        return this.eatInOrderAcceptService.accept(order);
    }

    @Override
    public Order serve(Order order) {
        return this.eatInOrderServeService.serve(order);
    }

    @Override
    public Order complete(Order order) {
        return eatInOrderCompleteService.complete(order);
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
        return orderType.equals(OrderType.EAT_IN);
    }
}
