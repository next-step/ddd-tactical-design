package kitchenpos.menus.application.menugroup.dto;

import java.math.BigDecimal;

public class MenuGroupRequest {
    private final String name;

    private final BigDecimal price;

    public MenuGroupRequest(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
