package kitchenpos.menus.domain;

import kitchenpos.menus.domain.exception.InvalidMenuProductsPriceException;
import kitchenpos.menus.domain.vo.MenuName;
import kitchenpos.menus.domain.vo.MenuPrice;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;

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
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

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

    public Menu(UUID id, String name, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return new MenuProducts(menuProducts);
    }

    public MenuProducts getMenuProducts(ProductService productService) {
        return new MenuProducts(menuProducts);
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public Menu changePrice(BigDecimal price) {
        this.price = new MenuPrice(price).getPrice();
        ;
        return this;
    }

    public Menu displayed() {
        this.displayed = true;
        return this;
    }

    public Menu hide() {
        this.displayed = false;
        return this;
    }

    public Menu checkMenuProductPrice() {
        BigDecimal sum = sumMenuProductPrice();

        if (price.compareTo(sum) > 0) {
            throw new InvalidMenuProductsPriceException();
        }
        return this;
    }

    public Menu checkMenuProductPrice(BigDecimal price) {
        BigDecimal sum = sumMenuProductPrice();

        if (price.compareTo(sum) > 0) {
            throw new InvalidMenuProductsPriceException();
        }
        return this;
    }

    private BigDecimal sumMenuProductPrice() {
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

    public Menu checkMenuProductPrice(ProductService productService) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts) {
            final Product product = productService.findById(menuProduct.getProductId());
            sum = sum.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }

        if (price.compareTo(sum) > 0) {
            throw new InvalidMenuProductsPriceException();
        }
        return this;
    }

}
