package kitchenpos.order.eatinorder.application;

import java.util.UUID;
import kitchenpos.order.common.domain.Order;

public interface EatInOrderService {

    Order create(final Order order);

    Order accept(final UUID orderId);

    Order serve(final UUID orderId);

    Order complete(final UUID orderId);
}
