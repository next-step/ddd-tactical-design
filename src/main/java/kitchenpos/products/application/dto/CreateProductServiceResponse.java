package kitchenpos.products.application.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductServiceResponse(UUID id, String name, BigDecimal price) {

    public static CreateProductServiceResponse from(final Product product) {
        return new CreateProductServiceResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
