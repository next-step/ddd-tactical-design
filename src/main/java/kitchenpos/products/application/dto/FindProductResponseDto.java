package kitchenpos.products.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.model.Product;

public record FindProductResponseDto(UUID id, String name, BigDecimal price) {

    public static FindProductResponseDto from(Product product) {
        return new FindProductResponseDto(
            product.getId(),
            product.getName().getValue(),
            product.getPrice().getValue()
        );
    }
}
