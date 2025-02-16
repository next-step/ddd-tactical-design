package kitchenpos.global.infrastructure.external;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.order.domain.service.DeliveryKitchenridersClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultKitchenridersClient implements DeliveryKitchenridersClient {

    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount,
        final String deliveryAddress) {
    }
}
