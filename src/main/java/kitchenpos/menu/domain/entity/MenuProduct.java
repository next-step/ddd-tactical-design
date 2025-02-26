package kitchenpos.menu.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.product.domain.model.ProductId;

@Table(name = "menu_product")
@Entity
public class MenuProduct {

    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "product_id"))
    private ProductId productId;

    @Embedded
    private MenuProductQty quantity;


    protected MenuProduct() {}

    public MenuProduct(ProductId productId, MenuProductQty quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public MenuProductQty getQuantity() {
        return quantity;
    }

    public ProductId getProductId() {
        return productId;
    }
}
