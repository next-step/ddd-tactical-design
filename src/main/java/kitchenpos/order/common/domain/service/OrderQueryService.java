package kitchenpos.order.common.domain.service;

import java.util.List;
import kitchenpos.order.common.domain.model.OrderVo;

public interface OrderQueryService {
    List<OrderVo.OrderInfo> findAll();

}
