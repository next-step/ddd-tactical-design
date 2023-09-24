package kitchenpos.eatinorders.domain.order;

import java.util.UUID;

import kitchenpos.eatinorders.domain.order.vo.MenuVo;

public interface MenuApiRepository {
    MenuVo findById(UUID menuId);
}
