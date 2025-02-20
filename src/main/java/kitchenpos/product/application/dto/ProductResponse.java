package kitchenpos.product.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.product.domain.model.ProductVo;

public record ProductResponse() {
    public record GetProduct(
        UUID id,
        String name,
        BigDecimal price
    ) {
        public static GetProduct fromVo(ProductVo.ProductInfo vo) {
            return new GetProduct(vo.productId(), vo.name().name(), vo.price().price());
        }
    }
}
