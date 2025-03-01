package kitchenpos.tobe.product.application.dto;

import kitchenpos.tobe.product.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductResponse(
        UUID id,
        String name,
        BigDecimal price
) {
    public static CreateProductResponse from(Product product) {
        return new CreateProductResponse(
                product.getId().getId(),
                product.getName().getName(),
                product.getPrice().getAmount()
        );
    }
}
