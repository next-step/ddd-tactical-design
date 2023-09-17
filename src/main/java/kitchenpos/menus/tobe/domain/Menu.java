package kitchenpos.menus.tobe.domain;

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
        Menu menu = new Menu();
        menu.id = UUID.randomUUID();
        menu.name = name;
        menu.price = price;
        menu.menuGroup = menuGroup;
        menu.displayed = displayed;
        menu.menuProducts = menuProducts;

        if (isMenuPriceLowerThanMenuProductPriceSum(menu.price, menu.menuProducts)) {
            throw new IllegalArgumentException("메뉴 가격은 메뉴 상품 가격의 합보다 작거나 같아야 합니다.");
        }
        validateMenuProductsIsEmpty(menuProducts);

        return menu;
    }

    private static void validateMenuProductsIsEmpty(List<MenuProduct> menuProducts) {
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

        if (isMenuPriceLowerThanMenuProductPriceSum(this.price, this.menuProducts)) {
            throw new IllegalArgumentException("메뉴 가격은 메뉴 상품 가격의 합보다 작거나 같아야 합니다.");
        }

        return true;
    }

    private static boolean isMenuPriceLowerThanMenuProductPriceSum(MenuPrice menuPrice, List<MenuProduct> menuProducts) {
        if (isNull(menuProducts)) {
            return true;
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts) {
            sum = sum.add(
                    menuProduct.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }

        if (menuPrice.getValue().compareTo(sum) > 0) {
            return true;
        }

        return false;
    }

    public void display() {
        if (isMenuPriceLowerThanMenuProductPriceSum(this.price, this.menuProducts)) {
            throw new IllegalStateException("메뉴 가격은 메뉴 상품 가격의 합보다 크면 메뉴를 노출할 수 없습니다.");
        }

        this.displayed = MenuDisplay.create(true);
    }

    public void hide() {
        this.displayed = MenuDisplay.create(false);
    }
}
