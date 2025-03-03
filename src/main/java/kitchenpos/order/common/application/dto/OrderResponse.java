package kitchenpos.order.common.application.dto;

import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.order.common.domain.model.OrderVo;

public record OrderResponse() {
    public record GetOrder(
        UUID id
    ) {
        public static GetOrder fromVo(OrderVo.OrderInfo vo) {
            return new GetOrder(
                           vo.getOrderId()
            );
        }
    }

    public record GetMenuProduct(
        UUID productId,
        long quantity
    ) {
        public static GetMenuProduct fromVo(MenuProduct vo) {
            return new GetMenuProduct(vo.getProductId().get(), vo.getQuantity().get());
        }
    }
}
