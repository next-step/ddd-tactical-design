package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuChangePriceResponse {
    private UUID id;
    private BigDecimal changedPrice;

    public MenuChangePriceResponse(UUID id, BigDecimal changedPrice) {
        this.id = id;
        this.changedPrice = changedPrice;
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getChangedPrice() {
        return changedPrice;
    }
}
