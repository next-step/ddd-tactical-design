package kitchenpos.order.eatin.domain.service;

import java.util.List;
import kitchenpos.order.common.domain.model.OrderId;
import kitchenpos.order.common.domain.model.OrderLineItems;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.eatin.domain.model.EatInOrder;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Create;
import kitchenpos.order.eatin.domain.model.OrderTableVo.OrderTableInfo;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Update;

public interface EatInService {

    List<OrderTableInfo> findAll();

    OrderTableInfo create(Create request);

    OrderTableInfo sit(OrderTableId orderTableId);

    OrderTableInfo clear(OrderTableId orderTableId);

    OrderTableInfo changeNumberOfGuests(Update request);

    void complete(OrderTableId orderTableId);

    EatInOrder createEatInOrder(OrderId orderId, OrderVo.Create request, OrderLineItems orderLineItems);
}
