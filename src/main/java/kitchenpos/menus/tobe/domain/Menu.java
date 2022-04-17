package kitchenpos.menus.tobe.domain;

import common.domain.Price;
import kitchenpos.products.infra.PurgomalumClient;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private MenuName name;

    @Embedded
    private Price price;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "varbinary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_to_menu_group")
    )
    private MenuGroup menuGroup;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(PurgomalumClient purgomalumClient, String name, BigDecimal price, MenuGroup menuGroup, boolean displayed, List<MenuProduct> menuProducts) {
        this.id = UUID.randomUUID();
        this.name = new MenuName(purgomalumClient, name);
        this.price = new Price(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(menuProducts);
    }

    public Menu(PurgomalumClient purgomalumClient, String name, BigDecimal price, boolean displayed, List<MenuProduct> menuProducts) {
        this.id = UUID.randomUUID();
        this.name = new MenuName(purgomalumClient, name);
        this.price = new Price(price);
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(menuProducts);
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

    public void changePrice(Price price) {
        this.price = price;
    }

    public void show() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }
}
