package kitchenpos.integration.doubles;

import kitchenpos.deliveryorders.infra.KitchenridersClient;

import java.math.BigDecimal;
import java.util.UUID;

public class FakeRidersClient implements KitchenridersClient {
    @Override
    public void requestDelivery(UUID orderId, BigDecimal amount, String deliveryAddress) {
    }
}
