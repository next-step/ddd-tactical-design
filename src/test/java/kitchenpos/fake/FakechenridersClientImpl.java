package kitchenpos.fake;


import kitchenpos.deliveryorder.infra.KitchenridersClient;

import java.math.BigDecimal;
import java.util.UUID;

public class FakechenridersClientImpl implements KitchenridersClient {
    @Override
    public void requestDelivery(UUID orderId, BigDecimal amount, String deliveryAddress) {
        // do nothing
    }
}
