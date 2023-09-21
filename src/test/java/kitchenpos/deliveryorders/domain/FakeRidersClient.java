package kitchenpos.deliveryorders.domain;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import kitchenpos.deliveryorders.infra.KitchenridersClient;

@Component
public class FakeRidersClient implements KitchenridersClient {
    @Override
    public void requestDelivery(UUID orderId, BigDecimal amount, String deliveryAddress) {
        //do nothing
    }
}
