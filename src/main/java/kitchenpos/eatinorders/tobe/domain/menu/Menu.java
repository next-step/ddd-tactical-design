package kitchenpos.eatinorders.tobe.domain.menu;

import java.math.BigDecimal;
import java.util.UUID;

public class Menu {
    private UUID id;
    private String name;
    private BigDecimal price;
    private MenuGroup menuGroup;
    private boolean displayed;

    protected Menu() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
