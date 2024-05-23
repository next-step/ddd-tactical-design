package kitchenpos.order.infra;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Component;

import kitchenpos.order.domain.KitchenridersClient;

@Component
public class DefaultKitchenridersClient implements KitchenridersClient {
    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount, final String deliveryAddress) {
    }
}
