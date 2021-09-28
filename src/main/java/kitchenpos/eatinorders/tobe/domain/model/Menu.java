package kitchenpos.eatinorders.tobe.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Menu {

    private UUID id;

    private String displayedName;

    private BigDecimal price;

    private boolean displayed;

    public Menu(UUID id, String displayedName, BigDecimal price, boolean displayed) {
        this.id = id;
        this.displayedName = displayedName;
        this.price = price;
        this.displayed = displayed;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayedName() {
        return displayedName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
