package kitchenpos.menus.domain;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    private Quantity quantity;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    public MenuProduct() {
    }

    public MenuProduct(Long quantity, UUID productId) {
        this.quantity = new Quantity(quantity);
        this.productId = productId;
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity.getValue();
    }
    
    public UUID getProductId() {
        return productId;
    }

}
