package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;

public record CreateProductResponseDto(UUID id, String name, BigDecimal price) {

    public static CreateProductResponseDto from(Product product) {
        return new CreateProductResponseDto(
            product.getId(),
            product.getName().getValue(),
            product.getPrice().getValue()
        );
    }
}
