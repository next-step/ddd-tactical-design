package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class TobeMenu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private MenuPrice menuPrice;

    @ManyToOne(optional = false)
    @JoinColumn(
        name = "menu_group_id",
        columnDefinition = "varbinary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private TobeMenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected TobeMenu() {
    }

    public TobeMenu(MenuName name, MenuPrice menuPrice, TobeMenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this(
            UUID.randomUUID(),
            name,
            menuPrice,
            menuGroup,
            displayed,
            menuProducts
        );
    }

    public TobeMenu(UUID id, MenuName name, MenuPrice menuPrice, TobeMenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        validationMenuPriceOverSumPrice(menuPrice, menuProducts);
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
        return name.getName();
    }

    public BigDecimal getMenuPrice() {
        return menuPrice.getPrice();
    }

    public TobeMenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        this.displayed = true;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public void changePrice(MenuPrice menuPrice) {
        validationMenuPriceOverSumPrice(menuPrice, this.menuProducts);
        menuPrice.changePrice(menuPrice.getPrice());
    }

    public boolean isMenuPriceOverSumPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        return menuPrice.getPrice().compareTo(menuProducts.sumMenuProductPrice()) > 0;
    }

    private void validationMenuPriceOverSumPrice(MenuPrice menuPrice, MenuProducts menuProducts) {
        if (isMenuPriceOverSumPrice(menuPrice, menuProducts)) {
            throw new IllegalArgumentException();
        }
    }

}
