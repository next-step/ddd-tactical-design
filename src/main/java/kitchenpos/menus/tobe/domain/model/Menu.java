package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.DisplayedName;
import kitchenpos.global.domain.vo.Price;

import java.util.List;
import java.util.UUID;

public final class Menu {

    private UUID id;
    private MenuGroup group;
    private DisplayedName name;
    private Price price;
    private MenuProducts menuProducts;
    private boolean displayed;

    public Menu(MenuGroup group, DisplayedName name, Price price, boolean displayed, List<MenuProduct> products) {
        this.id = UUID.randomUUID();
        this.group = group;
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuProducts = new MenuProducts(products);
        verifyMenuPricePolicy(this.price, this.menuProducts.getTotalPrice());
    }

    public boolean isDisplayed() {
        return displayed && price.lessThanEquals(menuProducts.getTotalPrice());
    }

    public void show() {
        verifyMenuPricePolicy(this.price, this.menuProducts.getTotalPrice());
        this.displayed = true;
    }

    private void verifyMenuPricePolicy(Price menuPrice, Price menuProductsPrice) {
        if (!menuPrice.lessThanEquals(menuProductsPrice)) {
            throw new IllegalStateException("메뉴 가격 정책을 위반하였습니다. 메뉴의 가격은 상품들의 가격보다 같거나 낮아야합니다.");
        }
    }

    public void hide() {
        this.displayed = false;
    }

    public void changePrice(Price price) {
        verifyMenuPricePolicy(price, this.menuProducts.getTotalPrice());
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }
}
