package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.tobe.Product;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    private UUID productId;

    @Embedded
    private MenuQuantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(Product product, MenuQuantity quantity) {
        this(null, product.getId(), quantity);
    }

    public MenuProduct(Long seq, UUID productId, MenuQuantity quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
    }

    public BigDecimal calculateSum(Product product) {
        return product.getPrice().multiply(quantity.getQuantity());
    }

    public UUID getProductId() {
        return productId;
    }
}
