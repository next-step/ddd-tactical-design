package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.model.ProductPrice;

public record ChangeProductPriceRequestDto(BigDecimal price) {

    public ProductPrice toValueObject() {
        return new ProductPrice(price);
    }
}
