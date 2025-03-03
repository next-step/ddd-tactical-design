package kitchenpos.order.common.domain.model;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.model.MenuGroupId;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuName;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuProducts;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.order.common.domain.entity.Order;

public record OrderVo() {

    public record OrderInfo(
        OrderId id
    ) {
        public static OrderInfo fromEntity(Order entity) {
            return new OrderInfo(entity.getOrderId());
        }

        public UUID getOrderId() {
            return id.get();
        }

    }

    public record Create(
        String name,
        MenuPrice price,
        MenuGroupId menuGroupId,
        boolean displayed,
        MenuProducts menuProducts
    ) {
    }

    public record Update(MenuId menuId, MenuPrice price) {}
}
