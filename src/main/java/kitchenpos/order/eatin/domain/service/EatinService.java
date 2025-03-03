package kitchenpos.order.eatin.domain.service;

import java.util.List;
import kitchenpos.order.eatin.domain.model.OrderTableId;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Create;
import kitchenpos.order.eatin.domain.model.OrderTableVo.OrderTableInfo;
import kitchenpos.order.eatin.domain.model.OrderTableVo.Update;

public interface EatinService {

    List<OrderTableInfo> findAll();

    OrderTableInfo create(Create request);

    OrderTableInfo sit(OrderTableId orderTableId);

    OrderTableInfo clear(OrderTableId orderTableId);

    OrderTableInfo changeNumberOfGuests(Update request);

    void complete(OrderTableId orderTableId);
}
