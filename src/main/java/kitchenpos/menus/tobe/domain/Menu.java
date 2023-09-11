package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.vo.MenuPrice;
import kitchenpos.menus.vo.MenuProducts;
import kitchenpos.products.application.ProductService;

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
        this.price = new MenuPrice(price).getPrice();
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(menuProducts).getMenuProducts();
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

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void changePrice(BigDecimal price) {
        this.price = new MenuPrice(price).getPrice();;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void displayed() {
        this.displayed = true;
    }
    public void hide() {
        this.displayed = false;
    }
}
