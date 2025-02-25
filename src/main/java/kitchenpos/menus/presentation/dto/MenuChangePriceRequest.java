package kitchenpos.menus.presentation.dto;

import java.util.UUID;

public class MenuChangePriceRequest {

    private UUID id;

    private long price;

    public MenuChangePriceRequest(UUID id, long price) {
        this.id = id;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public long getPrice() {
        return price;
    }
}
