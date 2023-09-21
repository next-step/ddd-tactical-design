package kitchenpos.menus.application.menugroup.dto;

import kitchenpos.products.domain.Product;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuGroupResponse {
    private final UUID productId;

    private final String name;

    private final BigDecimal price;

    public MenuGroupResponse(Product product) {
        this.productId = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
