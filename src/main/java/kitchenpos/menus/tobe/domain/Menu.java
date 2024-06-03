package kitchenpos.menus.tobe.domain;

import jakarta.persistence.*;

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

    protected Menu() {

    }

    public Menu(String name, int price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        checkMenuProductAmount(price, menuProducts);

        this.id = UUID.randomUUID();
        this.name = new MenuName(name);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(UUID id, String name, int price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.id = id;
        this.name = new MenuName(name);
        this.price = new MenuPrice(price);
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changePrice(int price) {
        checkMenuProductAmount(price, this.menuProducts);
        this.price = new MenuPrice(price);
    }

    private void checkMenuProductAmount(int price, MenuProducts menuProducts) {
        if (menuProducts.totalAmount() < price) {
            throw new IllegalArgumentException();
        }
    }

    public void display() {
        checkMenuProductAmount(price.getValue(), menuProducts);
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name.getValue();
    }

    public int getPrice() {
        return price.getValue();
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
