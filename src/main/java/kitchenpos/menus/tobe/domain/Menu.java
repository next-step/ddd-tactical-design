package kitchenpos.menus.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import kitchenpos.common.vo.Price;
import kitchenpos.menus.application.tobe.exception.InvalidMenuPriceException;
import kitchenpos.products.tobe.domain.ProductId;

@Entity
public class Menu {

    @Column(name = "id", columnDefinition = "binary(16)")
    @EmbeddedId
    private MenuId id;

    @Column(name = "name", nullable = false)
    @Embedded
    private MenuName name;

    @Column(name = "price", nullable = false)
    @Embedded
    private Price price;

    @Column(name = "menu_group_id", nullable = false)
    @Embedded
    private MenuGroupId menuGroupId;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    @Embedded
    private MenuProducts menuProducts;

    protected Menu() {
    }

    public Menu(MenuName name, Price price, MenuGroupId menuGroupId, MenuProducts menuProducts, boolean displayed) {
        this(MenuId.generate(), name, price, menuGroupId, menuProducts, displayed);
    }

    public Menu(MenuId id, MenuName name, Price price, MenuGroupId menuGroupId, MenuProducts menuProducts, boolean displayed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;

        if (this.isPriceInvalid()) {
            throw new InvalidMenuPriceException("메뉴 가격이 메뉴상품 가격 총 합보다 큽니다");
        }
    }

    private boolean isPriceInvalid() {
        Price totalProductPrice = menuProducts.totalPrice();
        return price.isGreaterThan(totalProductPrice);
    }

    public boolean containProduct(ProductId productId) {
        return menuProducts.containsProduct(productId);
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

    public MenuId getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public MenuGroupId getGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }
}
