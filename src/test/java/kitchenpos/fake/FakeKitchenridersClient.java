package kitchenpos.fake;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.orders.delivery.domain.KitchenridersClient;

public class FakeKitchenridersClient implements KitchenridersClient {

    @Override
    public void requestDelivery(UUID orderId, BigDecimal amount,
            String deliveryAddress) {
    }
}
