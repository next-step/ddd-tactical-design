package kitchenpos.tobe.product.application.dto;

import kitchenpos.tobe.product.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductListResponse(
        UUID id,
        String name,
        BigDecimal price) {
    public static ProductListResponse from(Product product) {
        return new ProductListResponse(
                product.getId().getId(),
                product.getName().getName(),
                product.getPrice().getAmount()
        );
    }
}
