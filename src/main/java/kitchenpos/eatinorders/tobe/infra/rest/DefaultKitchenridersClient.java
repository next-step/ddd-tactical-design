package kitchenpos.eatinorders.tobe.infra.rest;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.interfaces.KitchenridersClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultKitchenridersClient implements KitchenridersClient {

    @Override
    public void requestDelivery(final UUID orderId, final BigDecimal amount, final String deliveryAddress) {
    }
}
