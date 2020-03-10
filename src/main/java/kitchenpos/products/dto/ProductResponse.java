package kitchenpos.products.dto;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class ProductResponse {
    private long id;
    private String name;
    private BigDecimal price;

    public ProductResponse(final long id, final String name, final BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static ProductResponse of(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getProductPrice().getValue()
        );
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
