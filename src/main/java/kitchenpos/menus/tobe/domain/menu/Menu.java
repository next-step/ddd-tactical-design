package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.support.domain.ProductPrice;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private MenuName name;

    @Column(name = "price", nullable = false)
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

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this(UUID.randomUUID(), name, price, menuGroup, displayed, menuProducts);
    }

    public Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static Menu from(MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        checkCreateComparedPrice(price, menuProducts);
        return new Menu(name, price, menuGroup, displayed, menuProducts);
    }

    public void changePrice(final MenuPrice price) {
        checkChangePriceComparedPrice(price);
        this.price = price;
    }

    public void changeMenuProductPrice(UUID productId, final BigDecimal productPrice) {
        menuProducts.changeProductsPrice(productId, ProductPrice.from(productPrice));
    }

    public boolean isPriceGreaterThanMenuProductsSum() {
        return this.price.priceValue().compareTo(this.menuProducts.sumPrice()) > 0;
    }

    public void display() {
        checkDisplayComparedPrice();
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.nameValue();
    }

    public BigDecimal getPrice() {
        return price.priceValue();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getValues();
    }

    private void checkDisplayComparedPrice() {
        if (this.price.priceValue().compareTo(this.menuProducts.sumPrice()) > 0) {
            throw new IllegalStateException();
        }
    }

    private void checkChangePriceComparedPrice(MenuPrice price) {
        if (price.priceValue().compareTo(menuProducts.sumPrice()) > 0) {
            throw new IllegalArgumentException();
        }
    }

    private static void checkCreateComparedPrice(MenuPrice price, MenuProducts menuProducts) {
        if (price.priceValue().compareTo(menuProducts.sumPrice()) > 0) {
            throw new IllegalArgumentException();
        }
    }
}
