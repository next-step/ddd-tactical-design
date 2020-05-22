package kitchenpos.products.tobe.controller.response;

import kitchenpos.products.tobe.domain.model.Product;

import java.math.BigDecimal;

public class CreateProductResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private CreateProductResponse(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static CreateProductResponse of(Product product) {
        return new CreateProductResponse(product.getId(), product.getName(), product.getPrice().getPrice());
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
