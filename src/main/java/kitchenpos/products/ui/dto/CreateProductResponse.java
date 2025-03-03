package kitchenpos.products.ui.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateProductResponse(UUID id, String name, BigDecimal price) {

    public static CreateProductResponse from(final Product product) {
        return new CreateProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
