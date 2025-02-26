package kitchenpos.menu.ui.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.application.dto.SimpleMenuServiceRs;

public class SimpleMenuRs {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;

    public SimpleMenuRs(UUID id, String name, BigDecimal price, boolean isDisplayed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
    }

    public SimpleMenuRs(SimpleMenuServiceRs rs) {
        this.id = rs.getId();
        this.name = rs.getName();
        this.price = rs.getPrice();
        this.isDisplayed = rs.isDisplayed();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }
}
