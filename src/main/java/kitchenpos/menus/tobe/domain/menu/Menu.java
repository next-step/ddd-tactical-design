package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

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

    @Embedded
    private MenuDisplay displayed;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    protected Menu() {
    }

    protected Menu(UUID id, MenuName name, MenuPrice price, MenuGroup menuGroup, MenuDisplay displayed, List<MenuProduct> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public static Menu create(final MenuName name, final MenuPrice price, final MenuGroup menuGroup, final MenuDisplay displayed, final List<MenuProduct> menuProducts) {
        isMenuProductsEmpty(menuProducts);

        return new Menu(UUID.randomUUID(), name, price, menuGroup, displayed, menuProducts);
    }

    private static void isMenuProductsEmpty(List<MenuProduct> menuProducts) {
        if (isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴에는 1개 이상의 메뉴 상품이 포함되어야 합니다.");
        }
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public BigDecimal getPrice() {
        return price.getValue();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public Boolean isDisplayed() {
        return displayed.getValue();
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroup.getId();
    }

    public boolean changePrice(MenuPrice menuPrice) {
        if (this.price.equals(menuPrice)) {
            return false;
        }

        this.price = menuPrice;
        return true;
    }

    public void display() {
        this.displayed = MenuDisplay.create(true);
    }

    public void hide() {
        this.displayed = MenuDisplay.create(false);
    }
}
