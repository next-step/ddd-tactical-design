package kitchenpos.order.common.application.facade;

import kitchenpos.order.delivery.application.DeliveryUsecase;
import kitchenpos.order.eatin.application.EatinUsecase;
import kitchenpos.order.takeout.application.TakeoutUsecase;
import org.springframework.stereotype.Component;

@Component
public class OrderFacade {
    private final TakeoutUsecase takeoutUsecase;
    private final DeliveryUsecase deliveryUsecase;
    private final EatinUsecase eatinUsecase;

    public OrderFacade(TakeoutUsecase takeoutUsecase, DeliveryUsecase deliveryUsecase,
        EatinUsecase eatinUsecase) {
        this.takeoutUsecase = takeoutUsecase;
        this.deliveryUsecase = deliveryUsecase;
        this.eatinUsecase = eatinUsecase;
    }
}
