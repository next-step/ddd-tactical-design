package kitchenpos.products.tobe.application;

import java.util.UUID;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;

public class ProductCreationResponseDto {
    private final UUID id;
    private final String name;
    private final ProductPrice price;

    public ProductCreationResponseDto(UUID id, String name, ProductPrice price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductCreationResponseDto of(Product product) {
        return new ProductCreationResponseDto(product.getId(), product.getName(), product.getPrice());
    }
}
