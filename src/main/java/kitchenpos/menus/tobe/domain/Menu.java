package kitchenpos.menus.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

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
    private DisplayedName name;

    @Column(name = "price", nullable = false)
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

    @JoinColumn(
            name = "menu_id",
            nullable = false,
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu() {
    }

    public Menu(String name, Long price, MenuGroup menuGroup, boolean displayed,
                List<MenuProduct> menuProducts, PurgomalumClient purgomalumClient) {
        this.id = UUID.randomUUID();
        this.name = new DisplayedName(name, purgomalumClient);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        validateIfMenuPriceIsGreaterThanSumOfProductPrice();
    }

    public UUID getId() {
        return id;
    }

    public DisplayedName getName() {
        return name;
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

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void changePrice(Long amount) {
        validateIfMenuPriceIsGreaterThanSumOfProductPrice();
        price.changeAmount(amount);
    }

    public void display() {
        validateIfMenuPriceIsGreaterThanSumOfProductPrice();
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void hideMenuIfMenuPriceIsGreaterThanSumOfProductPrice() {
        if (isMenuPriceGreaterThanSumOfProductPrice()) {
            hide();
        }
    }

    private void validateIfMenuPriceIsGreaterThanSumOfProductPrice() {
        if (isMenuPriceGreaterThanSumOfProductPrice()) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isMenuPriceGreaterThanSumOfProductPrice() {
        return price.compareTo(getSumOfProductPrice()) > 0;
    }

    private BigDecimal getSumOfProductPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts) {
            sum = sum.add(
                    menuProduct.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }

        return sum;
    }
}
