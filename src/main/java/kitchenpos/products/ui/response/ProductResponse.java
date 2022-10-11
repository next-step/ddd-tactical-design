package kitchenpos.products.ui.response;

import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.products.domain.Product;

public class ProductResponse {

    private final UUID id;
    private final String name;
    private final BigDecimal price;

    public ProductResponse(UUID id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getNameValue(),
            product.getPriceValue()
        );
    }

    public static List<ProductResponse> of(List<Product> products) {
        return products.stream()
            .map(ProductResponse::from)
            .collect(toList());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
