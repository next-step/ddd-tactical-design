package kitchenpos.menus.domain;

import javax.persistence.*;
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

    @Transient
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(UUID id, DisplayedName name, Price price, MenuProducts menuProducts, MenuPricePolicy menuPricePolicy) {
        menuPricePolicy.follow(price, menuProducts.totalPrice());
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuProducts = menuProducts;
        this.displayed = true;
    }

    public void changePrice(Price price, MenuPricePolicy menuPricePolicy) {
        menuPricePolicy.follow(price, menuProducts.totalPrice());
        this.price = price;
    }

    public void display() {
        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public Price totalPrice() {
        return menuProducts.totalPrice();
    }

    public Price getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }


    public void changeProductPrice(UUID productId, Price price, MenuDisplayPolicy menuDisplayPolicy) {
        menuProducts.changeProductPrice(productId, price);
        menuDisplayPolicy.follow(this);
    }
}
