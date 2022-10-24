package kitchenpos.menus.tobe.dto.menu;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductRequest {

    private final String productName;
    private final BigDecimal productPrice;
    private UUID productId;

    public ProductRequest(UUID productId, String productName, BigDecimal productPrice) {
        this.productPrice = productPrice;
        this.productId = productId;
        this.productName = productName;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }
}
