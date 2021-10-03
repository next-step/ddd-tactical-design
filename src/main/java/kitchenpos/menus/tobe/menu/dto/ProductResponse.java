package kitchenpos.menus.tobe.menu.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductResponse {
    private UUID id;
    private String name;
    private BigDecimal price;

    protected ProductResponse() {
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
}
