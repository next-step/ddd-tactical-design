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

        validate();
    }

    private void validate() {
        if (this.isValidPriceForDisplay()) {
            throw new InvalidMenuPriceException("전시된 메뉴는 메뉴 가격이 메뉴상품 가격 총 합보다 클 수 없습니다.");
        }
    }

    private boolean isValidPriceForDisplay() {
        return this.displayed && this.isPriceInvalid(this.price);
    }

    private boolean isPriceInvalid(Price price) {
        Price totalProductPrice = menuProducts.totalPrice();
        return price.isGreaterThan(totalProductPrice);
    }

    public boolean containProduct(ProductId productId) {
        return menuProducts.containsProduct(productId);
    }

    public void changePrice(Price price) {
        if (this.isPriceInvalid(price)) {
            throw new InvalidMenuPriceException("메뉴상품 가격 총 합보다 큰 가격으로 변경할 수 없습니다");
        }
        this.price = price;
    }

    public void show() {
        if (this.isPriceInvalid(this.price)) {
            throw new InvalidMenuPriceException("메뉴 가격이 메뉴상품 가격 총 합보다 크면 전시할 수 없습니다");
        }
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
