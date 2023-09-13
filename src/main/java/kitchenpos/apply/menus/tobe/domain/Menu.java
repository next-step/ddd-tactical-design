package kitchenpos.apply.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "menu_id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private MenuName name;

    @Column(name = "price", nullable = false)
    private MenuPrice price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

    public static Menu of(MenuName name, BigDecimal price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts, MenuPriceChecker menuPriceChecker) {
        return new Menu(name, price, menuGroup, displayed, menuProducts, menuPriceChecker);
    }

    public Menu(
            MenuName name,
            BigDecimal price,
            MenuGroup menuGroup,
            boolean displayed,
            MenuProducts menuProducts,
            MenuPriceChecker menuPriceChecker
    ) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;


        if (menuPriceChecker.isTotalPriceLowerThanMenu(this.price.value(), menuProducts.getMenuProductList())) {
            throw new IllegalArgumentException();
        }
    }

    public void hideIfMenuPriceTooHigher(MenuPriceChecker menuPriceChecker) {
        if (menuPriceChecker.isTotalPriceLowerThanMenu(this.price.value(), menuProducts.getMenuProductList())) {
            this.displayed = false;
        }
    }

    public void changePrice(BigDecimal changePrice, MenuPriceChecker menuPriceChecker) {
        if (changePrice == null || changePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("변경할 가격은 0보다 커야합니다.");
        }

        if (menuPriceChecker.isTotalPriceLowerThanMenu(changePrice, menuProducts.getMenuProductList())) {
            throw new IllegalArgumentException();
        }
        this.price = new MenuPrice(changePrice);
    }

    public void setDisplayable(MenuPriceChecker menuPriceChecker) {
        if (menuPriceChecker.isTotalPriceLowerThanMenu(this.price.value(), menuProducts.getMenuProductList())) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void setHide() {
        this.displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public String getId() {
        return id.toString();
    }

    public BigDecimal getPrice() {
        return price.value();
    }

    public String getMenuGroupId() {
        return menuGroup.getId();
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public List<MenuProduct> getMenuProductList() {
        return menuProducts.getMenuProductList();
    }

    public List<Long> getMenuProductsSeq() {
        return menuProducts.getSequences();
    }

    public String getName() {
        return name.value();
    }
}

