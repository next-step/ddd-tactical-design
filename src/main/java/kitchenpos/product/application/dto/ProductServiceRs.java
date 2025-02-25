package kitchenpos.product.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.product.domain.model.Product;

public class ProductServiceRs {
    private UUID id;
    private String name;
    private BigDecimal price;

    public ProductServiceRs(Product product) {
        this.id = product.getId();
        this.name = product.getInnerName();
        this.price = product.getInnerPrice();
    }

    public ProductServiceRs() {
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
