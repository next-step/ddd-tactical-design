package kitchenpos.products.tobe.controller.request;

import kitchenpos.products.tobe.domain.model.Product;

import java.math.BigDecimal;

public class CreateProductRequest {

    private Long id;

    private String name;

    private BigDecimal price;

    public CreateProductRequest(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product toProduct() {
        return new Product(id, name, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
