package kitchenpos.products.dto;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductCreateRequest {

    private String name;
    private BigDecimal price;

    public ProductCreateRequest(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public Product toEntity(PurgomalumClient purgomalumClient) {
        return new Product(
                UUID.randomUUID(),
                new ProductName(this.name, purgomalumClient),
                new ProductPrice(this.price));
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}

