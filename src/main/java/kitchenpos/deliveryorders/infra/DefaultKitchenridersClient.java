package kitchenpos.deliveryorders.infra;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import kitchenpos.deliveryorders.domain.KitchenridersClient;

@Component
public class DefaultKitchenridersClient implements KitchenridersClient {
    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount, final String deliveryAddress) {
    }
}
