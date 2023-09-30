package kitchenpos.product.tobe.application.dto;

import java.util.UUID;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductName;
import kitchenpos.product.tobe.domain.ProductPrice;

public class ChangeProductPriceResponse {
    private final UUID id;
    private final ProductName name;
    private final ProductPrice price;

    private ChangeProductPriceResponse(UUID id, ProductName name, ProductPrice price) {
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

    public ProductPrice getPrice() {
        return price;
    }

    public static ChangeProductPriceResponse of(Product entity) {
        return new ChangeProductPriceResponse(
            entity.getId(),
            entity.getName(),
            entity.getPrice()
        );
    }
}
