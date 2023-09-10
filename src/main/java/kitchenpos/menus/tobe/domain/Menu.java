package kitchenpos.menus.tobe.domain;


import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuPriceException;
import kitchenpos.menus.tobe.domain.policy.ProfanityPolicy;

import javax.persistence.*;
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
    @Embedded
    private MenuDisplayedName name;

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

    public Menu(String name, ProfanityPolicy policy, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        this(new MenuDisplayedName(name, policy),
                new MenuPrice(price),
                menuGroup,
                displayed,
                new MenuProducts(menuProducts)
        );
    }

    public Menu(MenuDisplayedName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        validatePrice(price, menuProducts);
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changePrice(MenuPrice price) {
        validatePrice(price, this.menuProducts);
        this.price = price;
    }

    private static void validatePrice(MenuPrice price, MenuProducts menuProducts) {
        if (price.isGreaterThan(menuProducts.getSum())) {
            throw new MenuPriceException(MenuErrorCode.MENU_PRICE_IS_GREATER_THAN_PRODUCTS);
        }
    }


    public UUID getId() {
        return id;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void display() {
        validatePrice(this.price, this.menuProducts);
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroup.getId();
    }

    public MenuDisplayedName getName() {
        return name;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }
}
