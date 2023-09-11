package kitchenpos.menus.tobe.domain;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private Menu menu;

    @JoinColumn(
        name = "product_id",
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private UUID productId;

    @Embedded
    private MenuProductQuantity quantity = new MenuProductQuantity();

    public MenuProduct(Menu menu, UUID productId, long quantity) {
        assert isNotEmpty(menu);
        assert isNotEmpty(productId);

        this.menu = menu;
        this.productId = productId;
        this.quantity = new MenuProductQuantity(quantity);
    }

    protected MenuProduct() {
    }

    public Long getSeq() {
        return seq;
    }

    public Menu getMenu() {
        return menu;
    }

    public UUID getProductId() {
        return productId;
    }

    public MenuProductQuantity getQuantity() {
        return quantity;
    }
}
