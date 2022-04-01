package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.DisplayName;
import kitchenpos.global.domain.vo.Price;
import kitchenpos.global.infrastructure.external.BannedWordCheckClient;
import kitchenpos.menus.tobe.domain.exception.ViolationOfMenuPricePolicyException;

import java.math.BigDecimal;
import java.util.UUID;

public final class Menu {

    private UUID id;
    private MenuGroup group;
    private DisplayName name;
    private Price price;
    private MenuProducts menuProducts;
    private boolean displayed;

    public Menu(MenuGroup group, String name, BigDecimal price, BannedWordCheckClient bannedWordCheckClient, boolean displayed, MenuProduct... products) {
        this.id = UUID.randomUUID();
        this.group = group;
        this.name = new DisplayName(name, bannedWordCheckClient);
        this.price = new Price(price);
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
            throw new ViolationOfMenuPricePolicyException();
        }
    }

    public void hide() {
        this.displayed = false;
    }

    public void changePrice(BigDecimal price) {
        Price prize = new Price(price);
        verifyMenuPricePolicy(prize, this.menuProducts.getTotalPrice());
        this.price = prize;
    }

    public Price getPrice() {
        return price;
    }
}
