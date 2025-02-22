package kitchenpos.product.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.product.domain.model.ProductVo;

public record ProductRequest() {

    public record Create(
        @NotBlank(message = "상품명은 필수입니다.")
        String name,

        @PositiveOrZero(message = "상품 가격은 0원 이상이어야 합니다.")
        BigDecimal price
    ) {

        public ProductVo.Create toVo() {
            return new ProductVo.Create(name, ProductPrice.of(price));
        }
    }
    public record UpdatePrice(
        UUID productId,

        @PositiveOrZero(message = "상품가격은 0원 이상이어야 합니다.")
        BigDecimal price)
    {

        public ProductVo.Update toVo() {
            return new ProductVo.Update(productId, ProductPrice.of(price));
        }
    }

}
