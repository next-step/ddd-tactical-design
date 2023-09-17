package kitchenpos.menus.domain;

import kitchenpos.common.values.Name;
import kitchenpos.common.values.Price;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private Name name;

    @Column(name = "price", nullable = false)
    private Price price;

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

    @Column(name = "menu_group_id", insertable = false, updatable = false)
    private UUID menuGroupId;

    protected Menu() {
    }

    public Menu(Name name, Price price, UUID menuGroupId, List<MenuProduct> menuProducts, boolean displayed) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    public UUID getId() {

        return id;
    }


    public Name getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return this.menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public void display() {
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public void changePrice(Price price) {
        this.price = price;
    }
}
