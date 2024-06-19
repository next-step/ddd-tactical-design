package kitchenpos.menus.domain.tobe;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menugroups.domain.tobe.MenuGroup;

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
        this(UUID.randomUUID(), name, price, menuGroup.getId(), displayed, menuProducts);
    }

    public Menu(UUID id, MenuName name, MenuPrice price, UUID menuGroupId, DisplayedMenu displayed,
            MenuProducts menuProducts) {
        validateMenuPrice(price, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private static void validateMenuPrice(MenuPrice price, MenuProducts menuProducts) {
        if (price.isOver(menuProducts.calculateSumPrice())) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(MenuPrice price) {
        if (isOverThanProductSumPrice()) {
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
        return this.price.isOver(menuProducts.calculateSumPrice());
    }

    public void hide() {
        this.displayed = new DisplayedMenu(false);
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
