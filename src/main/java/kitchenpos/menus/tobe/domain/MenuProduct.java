package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", columnDefinition = "binary(16)", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    @Embedded
    private MenuProductQuantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, MenuProductQuantity quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public Long getQuantity() {
        return quantity.getValue();
    }

    public BigDecimal getAmount(BigDecimal productPrice) {
        return productPrice.multiply(BigDecimal.valueOf(quantity.getValue()));
    }

    public void setQuantity(long quantity) {
        this.quantity.setQuantity(quantity);
    }
}
