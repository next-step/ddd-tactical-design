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
    private final Long seq;

    private final UUID productId;

    private final BigDecimal price;

    @Embedded
    private final MenuQuantity quantity;

    public MenuProduct(Product product, MenuQuantity quantity) {
        this(null, product.getId(), product.getPrice(), quantity);
    }

    public MenuProduct(Long seq, UUID productId, BigDecimal price, MenuQuantity quantity) {
        this.seq = seq;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

    public BigDecimal calculateSum() {
        return price.multiply(quantity.getQuantity());
    }

    public UUID getProductId() {
        return productId;
    }
}
