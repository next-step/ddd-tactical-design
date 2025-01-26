package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private MenuPrice menuPrice;

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

    public static Menu newOne(String name, BigDecimal price, boolean displayed, MenuGroup menuGroup, MenuProducts menuProducts, PurgomalumClient purgomalumClient) {
        var menuPrice = new MenuPrice(price);
        MenuNamePolicy.validMenuName(name, purgomalumClient);
        AmountPolicy.validRegistrableMenuPrice(menuPrice, menuProducts);
        return new Menu(UUID.randomUUID(), name, menuPrice, menuGroup, displayed, menuProducts);
    }

    public Menu() {
    }

    public Menu(UUID id, String name, MenuPrice menuPrice, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.menuPrice = menuPrice;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return menuPrice.getPrice();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return this.menuProducts.getMenuProducts();
    }

    public void updateMenuProductPrice(UUID productId, BigDecimal productPrice) {
        this.menuProducts.updateProductPrice(productId, productPrice);
        if (AmountPolicy.isAmountOver(this.menuPrice, this.menuProducts)) {
            this.displayed = false;
        }
    }

    public void updateMenuPrice(BigDecimal price) {
        MenuPrice newMenuPrice = new MenuPrice(price);
        AmountPolicy.validUpdatableMenuPrice(newMenuPrice, this.menuProducts);
        this.menuPrice = newMenuPrice;
    }

    public void display() {
        AmountPolicy.validDisplayableMenuPrice(this.menuPrice, this.menuProducts);
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }
}
