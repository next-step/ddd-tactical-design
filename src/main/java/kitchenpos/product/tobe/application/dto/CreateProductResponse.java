package kitchenpos.product.tobe.application.dto;

import java.util.UUID;
import kitchenpos.common.Price;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductName;

public class CreateProductResponse {

    private final UUID id;
    private final ProductName name;
    private final Price price;

    private CreateProductResponse(UUID id, ProductName name, Price price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public static CreateProductResponse of(Product entity) {
        return new CreateProductResponse(
            entity.getId(),
            entity.getName(),
            entity.getPrice()
        );
    }
}
