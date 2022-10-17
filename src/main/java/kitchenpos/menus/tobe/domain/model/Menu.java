package kitchenpos.menus.tobe.domain.model;

import kitchenpos.common.DisplayedName;
import kitchenpos.menus.tobe.domain.exception.IllegalMenuPriceException;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.UUID;

@Entity(name = "TobeMenu")
@Table(name = "menu")
public class Menu {

    @Id
    @Column(name = "id", columnDefinition = "binary(16)")
    private UUID id;
    @Embedded
    private DisplayedName name;
    @Embedded
    private MenuPrice price;
    @Column(name = "menu_group_id", columnDefinition = "binary(16)", nullable = false)
    private UUID menuGroupId;
    @Column(name = "displayed", nullable = false)
    private boolean displayed;
    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(UUID id, DisplayedName name, long price, UUID menuGroupId, boolean displayed, MenuProduct... menuProducts) {
        this(id, name, new MenuPrice(price), menuGroupId, displayed, new MenuProducts(Arrays.asList(menuProducts)));
    }

    public Menu(
        UUID id,
        DisplayedName name,
        MenuPrice price,
        UUID menuGroupId,
        boolean displayed,
        MenuProducts menuProducts
    ) {
        validatePricePolicy(price, menuProducts);
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public void changePrice(MenuPrice updatePrice) {
        validatePricePolicy(updatePrice, menuProducts);
        this.price = updatePrice;
    }

    public void show() {
        validatePricePolicy(price, menuProducts);
        displayed = true;
    }

    public void hide() {
        displayed = false;
    }

    public void updateProduct(UUID productId, Long changedPrice) {
        menuProducts.update(productId, changedPrice);

        if (price.isGreaterThan(menuProducts.amounts())) {
            displayed = false;
        }
    }

    private void validatePricePolicy(MenuPrice price, MenuProducts menuProducts) {
        if (price.isGreaterThan(menuProducts.amounts())) {
            throw new IllegalMenuPriceException();
        }
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public boolean containsProduct(UUID productId) {
        return menuProducts.containsProduct(productId);
    }

    public UUID getId() {
        return id;
    }

    public long getPrice() {
        return price.getPrice();
    }
}
