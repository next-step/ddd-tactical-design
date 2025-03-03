package kitchenpos.order.common.application.dto;

import java.util.UUID;
import kitchenpos.menu.domain.entity.MenuProduct;
import kitchenpos.order.eatin.domain.model.OrderTableVo;

public record OrderTableResponse() {
    public record GetOrderTable(
        UUID id
    ) {
        public static GetOrderTable fromVo(OrderTableVo.OrderTableInfo vo) {
            return new GetOrderTable(
                           vo.getOrderTableId()
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
