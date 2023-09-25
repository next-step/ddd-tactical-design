package kitchenpos.menus.tobe.domain.menu;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @JoinColumn(
            name = "product_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private UUID productId;
    @Embedded
    private MenuProductQuantity quantity;

    @Embedded
    private MenuProductPrice menuProductPrice;

    protected MenuProduct() {
    }

    public MenuProduct(UUID productId, MenuProductQuantity quantity, MenuProductPrice menuProductPrice) {
        this.productId = productId;
        this.quantity = quantity;
        this.menuProductPrice = menuProductPrice;
    }

    public static MenuProduct of(UUID productId, MenuProductQuantity quantity, ProductClient productClient) {
        return new MenuProduct(productId, quantity, new MenuProductPrice(productClient.getProductPrice(productId)));
    }

    public Long getSeq() {
        return seq;
    }

    public long getQuantity() {
        return quantity.getMenuProductQuantity();
    }

    public UUID getProductId() {
        return productId;
    }

    public BigDecimal getMenuProductPrice() {
        return menuProductPrice.getMenuProductPrice();
    }
}
