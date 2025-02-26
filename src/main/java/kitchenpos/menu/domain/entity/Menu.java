package kitchenpos.menu.domain.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import kitchenpos.menu.domain.model.MenuGroupId;
import kitchenpos.menu.domain.model.MenuId;
import kitchenpos.menu.domain.model.MenuName;
import kitchenpos.menu.domain.model.MenuPrice;
import kitchenpos.menu.domain.model.MenuProducts;
import org.hibernate.annotations.DynamicUpdate;

@Table(name = "menu")
@Entity
@DynamicUpdate
public class Menu {

    @EmbeddedId
    private MenuId menuId;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "menu_group_id"))
    private MenuGroupId menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {}

    public Menu(MenuId menuId, MenuName name, MenuPrice price, MenuGroupId menuGroupId, boolean displayed, MenuProducts menuProducts) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
    }

    public MenuName getName() {
        return name;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

//    public BigDecimal getTotalMenuPrice() {
//        return menuProducts.calculateTotalPrice();
//    }

    public void updateDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public void updatePrice(MenuPrice price) {
        this.price = price;
    }

    public boolean isPriceLessThanOrEqual(BigDecimal diff) {
        return price.isLessThanOrEqual(diff);
    }

    public boolean isPriceEqual(BigDecimal diff) {
        return price.isEqual(diff);
    }
}
