package kitchenpos.menus.tobe.ui;

import java.math.BigDecimal;
import java.util.List;

public class MenuResponse {

    private final String id;

    private final String name;

    private final BigDecimal price;

    private final String menuGroupId;

    private final boolean displayed;

    private final List<Long> menuProductsSeq;

    public MenuResponse(String id, String name, BigDecimal price, String menuGroupId, boolean displayed, List<Long> menuProductsSeq) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductsSeq = menuProductsSeq;
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
