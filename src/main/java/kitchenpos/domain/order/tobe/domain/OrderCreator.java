package kitchenpos.domain.order.tobe.domain;

import org.springframework.stereotype.Component;

@Component
public class OrderCreator {

    public EatInOrder createEatIn(OrderInfo orderInfo, OrderTable orderTable) {
        // TODO validate
        return new EatInOrder();
    }

    public void createDelivery(OrderInfo orderInfo) {
        // TODO validate
    }

    public void createTakeout(OrderInfo orderInfo) {
        // TODO validate
    }
}
