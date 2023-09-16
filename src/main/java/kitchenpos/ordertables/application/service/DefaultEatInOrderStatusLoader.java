package kitchenpos.ordertables.application.service;

import kitchenpos.eatinorders.application.EatInOrderService;
import kitchenpos.eatinorders.domain.OrderTableId;
import kitchenpos.ordertables.application.EatInOrderStatusLoader;

import java.util.UUID;

public class DefaultEatInOrderStatusLoader implements EatInOrderStatusLoader {

    private EatInOrderService eatInOrderService;

    public DefaultEatInOrderStatusLoader(EatInOrderService eatInOrderService) {
        this.eatInOrderService = eatInOrderService;
    }

    @Override
    public boolean areAllOrdersComplete(UUID orderTableId) {
        OrderTableId targetId = new OrderTableId(orderTableId);
        return eatInOrderService.areAllOrdersComplete(targetId);

    }
}
