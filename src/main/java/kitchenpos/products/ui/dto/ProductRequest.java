package kitchenpos.products.ui.dto;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductRequest {

    private String name;

    private BigDecimal price;

    public ProductRequest(BigDecimal price) {
        this.price = price;
    }

    public ProductRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product toEntity(PurgomalumClient purgomalumClient) {
        return new Product(purgomalumClient, name, price);
    }
}
