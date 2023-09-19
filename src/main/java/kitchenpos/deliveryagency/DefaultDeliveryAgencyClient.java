package kitchenpos.deliveryagency;

import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class DefaultDeliveryAgencyClient implements DeliveryAgencyClient {

    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount, final String deliveryAddress) {
    }
}
