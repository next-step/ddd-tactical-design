package kitchenpos.order.deliveryorder.application;

import java.util.UUID;
import kitchenpos.order.common.domain.Order;

public interface DeliveryOrderService {

    Order create(final Order order);

    Order accept(final UUID orderId);

    Order serve(final UUID orderId);

    Order startDelivery(final UUID orderId);

    Order completeDelivery(final UUID deliveryId);

    Order complete(final UUID orderId);
}
