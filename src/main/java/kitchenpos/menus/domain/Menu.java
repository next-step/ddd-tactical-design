package kitchenpos.menus.domain;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private Price price;

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

    protected Menu() {
    }

    public Menu(UUID id, String name, PurgomalumClient purgomalumClient, Long price, MenuGroup menuGroup, MenuProducts menuProducts, MenuPricePolicy menuPricePolicy) {
        this(id, new DisplayedName(name, purgomalumClient), new Price(price), menuGroup, menuProducts, menuPricePolicy);
    }

    public Menu(UUID id, DisplayedName name, Price price, MenuGroup menuGroup, MenuProducts menuProducts, MenuPricePolicy menuPricePolicy) {
        menuPricePolicy.follow(price, menuProducts.totalPrice());
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.menuProducts = menuProducts;
        this.displayed = true;
    }

    public void changePrice(Price price, MenuPricePolicy menuPricePolicy) {
        menuPricePolicy.follow(price, menuProducts.totalPrice());
        this.price = price;
    }

    public void changeProductPrice(UUID productId, Price price, MenuDisplayPolicy menuDisplayPolicy) {
        menuProducts.changeProductPrice(productId, price);
        menuDisplayPolicy.follow(this);
    }

    public void display(MenuPricePolicy menuPricePolicy) {
        menuPricePolicy.follow(price, menuProducts.totalPrice());
        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public Price totalPrice() {
        return menuProducts.totalPrice();
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

}
