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
    private MenuPrice menuPrice;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(
        UUID id,
        MenuName menuName,
        MenuPrice price,
        boolean displayed,
        MenuProducts menuProducts,
        UUID menuGroupId
    ) {
        this.id = id;
        this.menuName = menuName;
        this.menuPrice = price;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public static Menu create(
        final UUID id,
        final String name,
        final BigDecimal price,
        final UUID menuGroupId,
        final boolean displayed,
        final MenuProducts menuProducts,
        final Predicate<String> predicate
    ) {
        return new Menu(
            id,
            new MenuName(name, predicate),
            new MenuPrice(price),
            displayed,
            menuProducts,
            menuGroupId
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

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public List<MenuProduct> getMenuProductList() {
        return menuProducts.toMenuProductList();
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public BigDecimal getSumOfMenuProductPrice() {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts.toMenuProductList()) {
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

    public BigDecimal multiplyPrice(BigDecimal multiplicand) {
        BigDecimal price = this.menuPrice.toBigDecimal();
        return price.multiply(multiplicand);
    }
}
