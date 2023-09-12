package kitchenpos.menus.tobe.domain;

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

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(String name, MenuPurgomalumClient menuPurgomalumClient,
                BigDecimal price, MenuGroup menuGroup, boolean displayed,
                List<MenuProduct> menuProducts, MenuPricePolicy menuPricePolicy) {
        this(UUID.randomUUID(),
                name,
                menuPurgomalumClient,
                price,
                menuGroup,
                displayed,
                menuProducts,
                menuGroup.getId(),
                menuPricePolicy);
    }

    private Menu(UUID id, String name, MenuPurgomalumClient menuPurgomalumClient,
                 BigDecimal price, MenuGroup menuGroup,
                 boolean displayed, List<MenuProduct> menuProducts, UUID menuGroupId,
                 MenuPricePolicy menuPricePolicy) {

        this.id = id;
        this.name = new MenuName(name, menuPurgomalumClient);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(menuProducts);
        this.menuGroupId = menuGroupId;

        validate(menuPricePolicy);
    }

    private void validate(MenuPricePolicy menuPricePolicy) {
        if (isDisplayed() && menuPricePolicy.isNotPermit(price, this)) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(BigDecimal price, MenuPricePolicy menuPricePolicy) {
        MenuPrice menuPrice = new MenuPrice(price);
        if (menuPricePolicy.isNotPermit(menuPrice, this)) {
            throw new IllegalArgumentException();
        }
        this.price = menuPrice;
    }

    public void display(MenuPricePolicy menuPricePolicy) {
        if (menuPricePolicy.isNotPermit(price, this)) {
            throw new IllegalStateException();
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void overMenuProductsTotalPriceThenHide(MenuPricePolicy menuPricePolicy) {
        if (menuPricePolicy.isNotPermit(price, this)) {
            hide();
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public BigDecimal getPrice() {
        return price.getPrice();
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

    public List<UUID> getProductIds() {
        return menuProducts.getProductIds();
    }
}
