package kitchenpos.order.domain.service;

import java.math.BigDecimal;
import java.util.UUID;

public interface DeliveryKitchenridersClient {

    void requestDelivery(UUID orderId, BigDecimal amount, String deliveryAddress);
}
