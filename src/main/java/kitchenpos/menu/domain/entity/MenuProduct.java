package kitchenpos.menu.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import kitchenpos.menu.application.dto.MenuRequest.MenuProductCreate;
import kitchenpos.menu.domain.model.MenuId;
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
    @AttributeOverride(name = "id", column = @Column(name = "menu_id"))
    @JsonIgnore
    private MenuId menuId;

    @Embedded
    private MenuProductQty quantity;

    protected MenuProduct() {}

    public MenuProduct(ProductId productId, MenuId menuId, MenuProductQty quantity) {
        this.productId = productId;
        this.menuId = menuId;
        this.quantity = quantity;
    }

    public MenuProductQty getQuantity() {
        return quantity;
    }

    public ProductId getProductId() {
        return productId;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public static MenuProduct fromDto(MenuProductCreate dto, MenuId menuId) {
        return new MenuProduct(ProductId.of(dto.productId()), menuId, MenuProductQty.of(dto.quantity()));
    }
}
