package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
public class Product {
    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "price")
    private BigDecimal productPrice;

    public Product() {
    }

    public Product(UUID productId, BigDecimal productPrice) {
        this.productId = productId;
        this.productPrice = productPrice;
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void updatePrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
}
