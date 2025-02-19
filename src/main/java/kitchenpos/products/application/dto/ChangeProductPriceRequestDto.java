package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.model.ProductPrice;

public class ChangeProductPriceRequestDto {

    private BigDecimal price;

    public ChangeProductPriceRequestDto() {

    }

    public ChangeProductPriceRequestDto(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public ProductPrice toValueObject() {
        return new ProductPrice(price);
    }
}
