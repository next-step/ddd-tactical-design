package kitchenpos.deliveryorders.infra;

import kitchenpos.deliveryorders.application.DeliveryAgencyClient;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class DefaultKitchenridersClient implements DeliveryAgencyClient {
    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount, final String deliveryAddress) {
    }
}
