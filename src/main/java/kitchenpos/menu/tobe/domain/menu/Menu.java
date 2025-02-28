package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;

import java.util.Collections;
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

    @Column(name = "menu_group_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuGroupId;

    @Embedded
    private MenuDisplayStatus displayed;

    private MenuProducts menuProducts;

    protected Menu() {
    }

    public static Menu of(MenuName name, MenuPrice price, UUID menuGroupId, MenuDisplayStatus displayed, MenuProducts menuProducts, MenuValidator menuValidator, ProductClient productClient) {
        menuValidator.validateMenuGroup(menuGroupId);
        productClient.validateMenuPrice(menuProducts, price);
        productClient.validateMenuProductSize(menuProducts);
        return new Menu(
                name,
                price,
                menuGroupId,
                displayed,
                menuProducts
        );
    }

    private Menu(MenuName name, MenuPrice price, UUID menuGroupId, MenuDisplayStatus displayed, MenuProducts menuProducts) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Long getMenuPrice() {
        return price.getValue();
    }

    public UUID getId() {
        return id;
    }

    public void changeMenuPrice(final Long price, final ProductClient productClient) {

        MenuPrice newPrice = MenuPrice.from(price);

        productClient.validateMenuPrice(menuProducts, newPrice);

        this.price = newPrice;
    }

    public void show(final ProductClient productClient) {
        productClient.validateMenuPrice(menuProducts, price);
        this.displayed.show();
    }

    public void hide() {
        this.displayed.hide();
    }

    public boolean isDisplayed() {
        return displayed.isDisplayed();
    }

    public String getName() {
        return name.getName();
    }

    public List<MenuProduct> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts.getMenuProducts());
    }


    public UUID getMenuGroupId() {
        return menuGroupId;
    }

}
