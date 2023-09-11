package kitchenpos.products.ui.response;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRes {

    private UUID id;

    private BigDecimal price;

    private String name;

    public ProductRes(UUID id,
                      BigDecimal price,
                      String name) {
        this.id = id;
        this.price = price;
        this.name = name;
    }

    public static ProductRes from(Product product) {
        return new ProductRes(product.getId(), product.getPrice(), product.getName());
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}
