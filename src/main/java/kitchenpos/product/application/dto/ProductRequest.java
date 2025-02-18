package kitchenpos.product.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.product.domain.model.ProductVo;

public record ProductRequest() {

    public record Create(
        String name,
        BigDecimal price
    ) {

        public ProductVo.Create toVo() {
            return new ProductVo.Create(name, price);
        }
    }
    public record UpdatePrice(UUID productId, BigDecimal price) {

        public ProductVo.Update toVo() {
            return new ProductVo.Update(productId, price);
        }
    }

}
