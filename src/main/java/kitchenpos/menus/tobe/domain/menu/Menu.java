package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.exception.MenuErrorCode;
import kitchenpos.menus.exception.MenuException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Table(name = "menu")
@Entity
public class Menu {

    @EmbeddedId
    private MenuId id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuDisplayedName name;

    @Embedded
    private Price price;


    @JoinColumn(
            name = "menu_group_id",
            columnDefinition = "binary(16)"
    )
    @Embedded
    private MenuGroupId menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(String name,
                ProfanityPolicy policy,
                BigDecimal price,
                MenuGroupId menuGroupId,
                boolean displayed,
                List<MenuProduct> menuProducts) {
        this(new MenuDisplayedName(name, policy),
                new Price(price),
                menuGroupId,
                displayed,
                new MenuProducts(menuProducts)
        );
    }

    public Menu(MenuDisplayedName name,
                Price price,
                MenuGroupId menuGroupId,
                boolean displayed,
                MenuProducts menuProducts) {
        if (displayed && price.isGreaterThan(menuProducts.calculateSum())) {
            throw new MenuException(MenuErrorCode.MENU_PRICE_IS_GREATER_THAN_PRODUCTS);
        }
        this.id = new MenuId();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public Menu(String name,
                ProfanityPolicy policy,
                long price,
                MenuGroupId menuGroupId,
                boolean displayed,
                MenuProducts menuProducts) {
        this(new MenuDisplayedName(name, policy),
                new Price(price),
                menuGroupId,
                displayed,
                menuProducts
        );
    }

    public void changePrice(Price price) {
        if (displayed && price.isGreaterThan(menuProducts.calculateSum())) {
            throw new MenuException(MenuErrorCode.MENU_PRICE_IS_GREATER_THAN_PRODUCTS);
        }

        this.price = price;
    }

    public void checkPriceAndHide() {
        if (price.isGreaterThan(menuProducts.calculateSum())) {
            hide();
        }
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void display() {
        if (price.isGreaterThan(menuProducts.calculateSum())) {
            throw new MenuException(MenuErrorCode.MENU_PRICE_IS_GREATER_THAN_PRODUCTS);
        }
        this.displayed = true;
    }

    public void hide() {
        this.displayed = false;
    }

    public MenuId getId() {
        return id;
    }

    public UUID getIdValue() {
        return id.getValue();
    }

    public MenuGroupId getMenuGroup() {
        return menuGroupId;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public List<ProductId> getProductIds() {
        return menuProducts.getProductIds();
    }

    public MenuDisplayedName getName() {
        return name;
    }

    public String getNameValue() {
        return name.getValue();
    }

    public BigDecimal getPriceValue() {
        return price.getValue();
    }

    public Price getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        return Objects.equals(id, menu.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void fetchProductPrice(ProductId productId, Price productPrice) {
        this.menuProducts.fetchPrice(productId, productPrice);
        checkPriceAndHide();
    }

    public UUID getMenuGroupIdValue() {
        return menuGroupId.getValue();
    }
}
