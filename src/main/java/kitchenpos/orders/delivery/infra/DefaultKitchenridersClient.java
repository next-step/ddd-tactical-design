package kitchenpos.orders.delivery.infra;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.orders.delivery.domain.KitchenridersClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultKitchenridersClient implements KitchenridersClient {

    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount,
            final String deliveryAddress) {
    }
}
