package kitchenpos.menus.tobe.domain.menuproduct;

import jakarta.persistence.*;

import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Embedded
    private Quantity quantity;

    private long price;

    protected MenuProduct() {

    }

    public static MenuProduct of(final UUID productId, final Quantity quantity, final long price) {
        return new MenuProduct(null, productId, quantity, price);
    }

    private MenuProduct(Long seq, UUID productId, Quantity quantity, long price) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public long getMenuProductPrice() {
        return price * quantity.getValue();
    }


    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity.getValue();
    }

    public long getPrice() {
        return price;
    }

}
