package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
        this.menuPrice = price;
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
        final Predicate<String> predicate
    ) {
        return new Menu(
            id,
            new MenuName(name, predicate),
            new MenuPrice(price),
            menuGroup,
            displayed,
            menuProducts,
            menuGroup.getId()
        );
    }

    public UUID getId() {
        return id;
    }

    public MenuName getMenuName() {
        return menuName;
    }

    public String getStringName() {
        return menuName.getName();
    }

    public MenuPrice getMenuPrice() {
        return menuPrice;
    }

    public BigDecimal getBigDecimalPrice() {
        return menuPrice.toBigDecimal();
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

    public boolean exceedsSum(final BigDecimal target) {
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

    public void changePrice(final BigDecimal price) {
        if (exceedsSum(price)) {
            throw new IllegalArgumentException();
        }
        this.menuPrice = new MenuPrice(price);
    }

    public static List<MenuProduct> createMenuProducts(final List<Product> products, final List<MenuProduct> menuProductRequests) {
        validateSize(products, menuProductRequests);
        return getFilteredMenuProducts(products, menuProductRequests);
    }

    private static List<MenuProduct> getFilteredMenuProducts(final List<Product> products, final List<MenuProduct> menuProductRequests) {
        return menuProductRequests.stream()
            .map(menuProductRequest -> {
                final long quantity = menuProductRequest.getQuantity();
                if (quantity < 0) {
                    throw new IllegalArgumentException();
                }
                final Product product = products.stream()
                    .filter(p -> p.getId().equals(menuProductRequest.getProductId()))
                    .findFirst()
                    .orElseThrow(NoSuchElementException::new);
                return new MenuProduct(product, quantity);
            })
            .collect(Collectors.toList());
    }

    private static void validateSize(final List<Product> products, final List<MenuProduct> menuProducts) {
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal multiplyPrice(BigDecimal multiplicand) {
        BigDecimal price = this.menuPrice.toBigDecimal();
        return price.multiply(multiplicand);
    }
}
