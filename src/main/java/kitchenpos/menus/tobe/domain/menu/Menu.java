package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuDisplayedName displayedName;

    @Embedded
    private MenuPrice price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(MenuDisplayedName displayedName, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this(null, displayedName, price, menuGroup, displayed, menuProducts);
    }

    public Menu(UUID id, MenuDisplayedName displayedName, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.validate(price, menuProducts);
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    private void validate(MenuPrice price, MenuProducts menuProducts) {
        if (price.isBiggerThan(menuProducts.totalAmount())) {
            throw new IllegalArgumentException();
        }
    }

    public void changeMenuProductPrice(UUID productId, BigDecimal price) {
        this.menuProducts.changeMenuProductPrice(productId, price);
        if (this.isPriceBiggerThanMenuProductsTotalAmount()) {
            this.displayed = false;
        }
    }

    private boolean isPriceBiggerThanMenuProductsTotalAmount() {
        MenuPrice totalAmount = this.menuProducts.totalAmount();
        return this.price.isBiggerThan(totalAmount);
    }

    public void changePrice(MenuPrice price) {
        if (price.isBiggerThan(this.menuProducts.totalAmount())) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public void display() {
        if (price.isBiggerThan(this.menuProducts.totalAmount())) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public BigDecimal priceMultiplyByQuantity(long quantity) {
        return this.price.multiplyByQuantity(quantity).getValue();
    }

    public UUID getId() {
        return id;
    }


    public MenuDisplayedName getDisplayedName() {
        return displayedName;
    }


    public MenuPrice getPrice() {
        return price;
    }


    public MenuGroup getMenuGroup() {
        return menuGroup;
    }


    public boolean isDisplayed() {
        return displayed;
    }


    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
