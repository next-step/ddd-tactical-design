package kitchenpos.menu.application.dto;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;

public class SimpleMenuServiceRs {
    private UUID id;
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;

    public SimpleMenuServiceRs(UUID id, String name, BigDecimal price, boolean isDisplayed) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
    }

    public SimpleMenuServiceRs(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getInnerName();
        this.price = menu.getInnerPrice();
        this.isDisplayed = menu.isDisplayed();
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
