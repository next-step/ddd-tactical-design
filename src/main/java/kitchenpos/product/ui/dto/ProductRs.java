package kitchenpos.product.ui.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.product.application.dto.ProductServiceRs;
import kitchenpos.product.domain.model.Product;

public class ProductRs {
    private UUID id;
    private String name;
    private BigDecimal price;

    public ProductRs(Product product) {
        this.id = product.getId();
        this.name = product.getInnerName();
        this.price = product.getInnerPrice();
    }

    public ProductRs(ProductServiceRs product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public ProductRs() {
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
