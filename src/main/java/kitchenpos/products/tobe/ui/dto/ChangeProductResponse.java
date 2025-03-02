package kitchenpos.products.tobe.ui.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ChangeProductResponse(UUID productId, BigDecimal price) {

    public static ChangeProductResponse from(final Product product) {
        return new ChangeProductResponse(
                product.getId(),
                product.getPrice()
        );
    }
}
