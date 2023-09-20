package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

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

    @Embedded
    private MenuName name;

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

    public Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static Menu of(MenuName name, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        return new Menu(UUID.randomUUID(), name, price, menuGroup, displayed, menuProducts);
    }

    protected Menu() {
    }

    public UUID getId() {
        return id;
    }
    public String getName() {
        return name.getMenuName();
    }

    public BigDecimal getPrice() {
        return price.getMenuPrice();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }
    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts.getMenuProducts();
    }

    public void changeMenuPrice(MenuPrice menuPrice) {
        this.price = menuPrice;
    }

    public void display() {
        this.displayed = true;
    }

    public void notDisplay() {
        this.displayed = false;
    }
}
