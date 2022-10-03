package kitchenpos.menus.domain;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
        name = "seq",
        unique = true,
        nullable = false
    )
    private Long seq;

    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false, columnDefinition = "binary(16)")
    private Menu menu;

    @Column(name = "product_id", length = 16, nullable = false, columnDefinition = "binary(16)")
    private UUID productId;

    @Embedded
    private MenuProductQuantity quantity;

    protected MenuProduct() {
    }

    public MenuProduct(Menu menu, UUID productId, MenuProductQuantity quantity) {
        this(null, menu, productId, quantity);
    }

    public MenuProduct(Long seq, Menu menu, UUID productId, MenuProductQuantity quantity) {
        this.seq = seq;
        this.productId = productId;
        this.quantity = quantity;
        this.menu = menu;
        this.menu.addMenuProduct(this);
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantityValue() {
        return quantity.getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }
}
