package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.tobe.domain.Product;

public class ProductCreationResponseDto {
    private final UUID id;
    private final String name;
    private final BigDecimal price;

    public ProductCreationResponseDto(UUID id,
                                      String name,
                                      BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductCreationResponseDto of(Product product) {
        return new ProductCreationResponseDto(product.getId(), product.getName(), product.getPrice());
    }
}
