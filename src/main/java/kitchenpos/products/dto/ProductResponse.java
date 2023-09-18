package kitchenpos.products.dto;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ProductResponse {
    private UUID id;

    private String name;

    private BigDecimal price;

    public ProductResponse(ProductId id, String name, BigDecimal price) {
        this.id = id.getValue();
        this.name = name;
        this.price = price;
    }

    public static ProductResponse fromEntity(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getNameValue(),
                product.getPriceValue());
    }
    public static List<ProductResponse> fromEntities(List<Product> products) {
        return products.stream()
                .map(ProductResponse::fromEntity)
                .collect(Collectors.toUnmodifiableList());
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getId() {
        return id;
    }

}
