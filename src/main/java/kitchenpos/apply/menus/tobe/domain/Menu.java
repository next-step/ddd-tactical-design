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

    @Embedded
    private MenuName name;

    @Embedded
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

    public static Menu of(MenuName name, BigDecimal price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts, BigDecimal totalPrice) {
        return new Menu(name, price, menuGroup, displayed, menuProducts, totalPrice);
    }

    public Menu(
            MenuName name,
            BigDecimal price,
            MenuGroup menuGroup,
            boolean displayed,
            MenuProducts menuProducts,
            BigDecimal totalPrice
    ) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;


        if (MenuPriceChecker.isTotalPriceLowerThanMenu(this.price.value(), totalPrice)) {
            throw new IllegalArgumentException();
        }
    }

    public void hideIfMenuPriceTooHigher(BigDecimal totalPrice) {
        if (MenuPriceChecker.isTotalPriceLowerThanMenu(this.price.value(), totalPrice)) {
            this.displayed = false;
        }
    }

    public void changePrice(BigDecimal changePrice, BigDecimal totalPrice) {
        if (changePrice == null || changePrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("변경할 가격은 0보다 커야합니다.");
        }

        if (MenuPriceChecker.isTotalPriceLowerThanMenu(changePrice, totalPrice)) {
            throw new IllegalArgumentException();
        }
        this.price = new MenuPrice(changePrice);
    }

    public void setDisplayable(BigDecimal totalPrice) {
        if (MenuPriceChecker.isTotalPriceLowerThanMenu(this.price.value(), totalPrice)) {
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

