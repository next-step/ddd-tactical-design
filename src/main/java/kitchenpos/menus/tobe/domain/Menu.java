package kitchenpos.menus.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    @Column(name = "name", nullable = false)
    private MenuName menuName;

    @Embedded
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

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "menu_id",
        nullable = false,
        columnDefinition = "binary(16)",
        foreignKey = @ForeignKey(name = "fk_menu_product_to_menu")
    )
    private List<MenuProduct> menuProducts;

    @Transient
    private UUID menuGroupId;

    public Menu() {
    }

    public Menu(
        UUID id,
        MenuName menuName,
        MenuPrice price,
        MenuGroup menuGroup,
        boolean displayed,
        List<MenuProduct> menuProducts,
        UUID menuGroupId
    ) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public static Menu create(
        final UUID id,
        final String name,
        final BigDecimal price,
        final MenuGroup menuGroup,
        final boolean displayed,
        final List<MenuProduct> menuProducts,
        final UUID menuGroupId,
        final Predicate<String> predicate
    ) {
        return new Menu(
            id,
            new MenuName(name, predicate),
            new MenuPrice(price),
            menuGroup,
            displayed,
            menuProducts,
            menuGroupId
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public MenuName getMenuName() {
        return menuName;
    }

    public MenuPrice getPrice() {
        return price;
    }

    public BigDecimal getBigDecimalPrice() {
        return price.toBigDecimal();
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

    public BigDecimal getSumOfMenuProductPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : this.getMenuProducts()) {
            sum = sum.add(
                menuProduct.getProduct()
                    .multiplyPrice(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }

    public boolean exceedsSum(BigDecimal target) {
        BigDecimal sum = getSumOfMenuProductPrice();
        return target.compareTo(sum) > 0;
    }

    public void hide() {
        this.displayed = false;
    }

    public void display() {
        if (exceedsSum(getBigDecimalPrice())) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void changePrice(BigDecimal price) {
        if (exceedsSum(price)) {
            throw new IllegalArgumentException();
        }
        this.price = new MenuPrice(price);
    }
}
