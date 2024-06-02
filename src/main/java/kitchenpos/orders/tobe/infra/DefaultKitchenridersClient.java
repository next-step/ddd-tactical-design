package kitchenpos.orders.tobe.infra;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import kitchenpos.orders.tobe.domain.KitchenridersClient;

@Component
public class DefaultKitchenridersClient implements KitchenridersClient {
    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount, final String deliveryAddress) {
    }
}
