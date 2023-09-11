package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.domain.Menu;

import java.math.BigDecimal;
import java.util.List;

public class MenuResponse {

    private final String id;

    private final String name;

    private final BigDecimal price;

    private final String menuGroupId;

    private final boolean displayed;

    private final List<Long> menuProductsSeq;

    public MenuResponse(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.menuGroupId = menu.getMenuGroupId();
        this.displayed = menu.isDisplayed();
        this.menuProductsSeq = menu.getMenuProductsSeq();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<Long> getMenuProductsSeq() {
        return menuProductsSeq;
    }
}
