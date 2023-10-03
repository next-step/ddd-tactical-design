package kitchenpos.deliveryorders.ui;

import kitchenpos.deliveryorders.application.DeliveryOrderService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryOrderRestController {

    private final DeliveryOrderService deliveryOrderService;

    public DeliveryOrderRestController(DeliveryOrderService deliveryOrderService) {
        this.deliveryOrderService = deliveryOrderService;
    }
}
