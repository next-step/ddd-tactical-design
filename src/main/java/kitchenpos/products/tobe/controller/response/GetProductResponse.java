package kitchenpos.products.tobe.controller.response;

import kitchenpos.products.tobe.domain.model.Product;

import java.math.BigDecimal;

public class GetProductResponse {

    private Long id;

    private String name;

    private BigDecimal price;

    private GetProductResponse(Long id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static GetProductResponse of(Product product) {
        return new GetProductResponse(product.getId(), product.getName(), product.getPrice().getPrice());
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
