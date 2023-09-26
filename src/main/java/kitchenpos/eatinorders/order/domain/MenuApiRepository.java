package kitchenpos.eatinorders.order.domain;

import java.util.UUID;

import kitchenpos.eatinorders.order.domain.vo.MenuVo;

public interface MenuApiRepository {
    MenuVo findById(UUID menuId);
}
