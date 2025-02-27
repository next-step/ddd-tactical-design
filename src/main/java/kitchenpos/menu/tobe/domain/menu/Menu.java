package kitchenpos.menu.tobe.domain.menu;

import jakarta.persistence.*;
import kitchenpos.common.tobe.Profanities;

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

    public static Menu of(MenuName name, MenuPrice price, UUID menuGroupId, MenuDisplayStatus displayed, MenuProducts menuProducts, MenuValidator menuValidator) {
        validate(price, menuGroupId, menuProducts, menuValidator);

        return new Menu(
                name,
                price,
                menuGroupId,
                displayed,
                menuProducts
        );
    }

    private static void validate(MenuPrice price, UUID menuGroupId, MenuProducts menuProducts, MenuValidator menuValidator) {
        menuValidator.validateMenuProductSize(menuProducts);
        menuValidator.validateMenuPrice(menuProducts, price);
        menuValidator.validateMenuGroup(menuGroupId);
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

    public void changeMenuPrice(final Long price, final MenuValidator menuValidator) {

        MenuPrice newPrice = MenuPrice.from(price);

        menuValidator.validateMenuPrice(menuProducts, newPrice);

        this.price = newPrice;
    }

    public void show(MenuValidator menuValidator) {
        menuValidator.validateMenuPrice(menuProducts, price);
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
        return Collections.unmodifiableList(menuProducts.getProducts());
    }

    public MenuProducts getMenuProduct() {
        return menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

}
