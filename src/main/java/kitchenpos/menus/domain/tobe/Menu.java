package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.products.domain.tobe.ProductPrice;

@Table(name = "menu")
@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice price;

    private UUID menuGroupId;

    @Embedded
    private DisplayedMenu displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(MenuName name, MenuPrice price, MenuGroup menuGroup, DisplayedMenu displayed,
        MenuProducts menuProducts) {
        validateMenuPrice(price, menuProducts);
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroup.getId();
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private static void validateMenuPrice(MenuPrice price, MenuProducts menuProducts) {
        if (price.isOver(menuProducts.calculateSumPrice())) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(MenuPrice price) {
        if (price.isOver(menuProducts.calculateSumPrice())) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public void display() {
        if (isOverThanProductSumPrice()) {
            throw new IllegalArgumentException();
        }
        this.displayed = new DisplayedMenu(true);
    }

    public boolean isOverThanProductSumPrice() {
        return price.isOver(menuProducts.calculateSumPrice());
    }

    public void hide() {
        this.displayed = new DisplayedMenu(false);
    }

    public void changeMenuProductPrice(UUID productId, ProductPrice price) {
        for (MenuProduct menuProduct : menuProducts) {
            if (menuProduct.getProductId().equals(productId)) {
                menuProduct.changePrice(price);
            }
        }

        if (isOverThanProductSumPrice()) {
            hide();
        }
    }

    public boolean isDisplayed() {
        return this.displayed.isDisplayed();
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price.getPrice();
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
