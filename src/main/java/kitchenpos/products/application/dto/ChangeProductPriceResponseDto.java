package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;

public record ChangeProductPriceResponseDto(UUID id, String name, BigDecimal price) {

    public static ChangeProductPriceResponseDto from(Product product) {
        return new ChangeProductPriceResponseDto(
            product.getId(),
            product.getName().getValue(),
            product.getPrice().getValue()
        );
    }
}
