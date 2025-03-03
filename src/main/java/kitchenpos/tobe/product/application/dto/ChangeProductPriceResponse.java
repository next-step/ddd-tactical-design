package kitchenpos.tobe.product.application.dto;

import kitchenpos.tobe.product.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ChangeProductPriceResponse(
        UUID id,
        String name,
        BigDecimal price
) {
    public static ChangeProductPriceResponse from(Product product) {
        return new ChangeProductPriceResponse(
                product.getId().getId(),
                product.getName().getName(),
                product.getPrice().getAmount()
        );
    }
}
