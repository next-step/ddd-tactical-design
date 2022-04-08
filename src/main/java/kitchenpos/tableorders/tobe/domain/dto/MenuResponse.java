package kitchenpos.tableorders.tobe.domain.dto;

import kitchenpos.global.domain.vo.Price;

import java.util.UUID;

public class MenuResponse {

    private UUID id;
    private Price price;
    private boolean displayed;

    public MenuResponse() {
    }

    public MenuResponse(UUID id, Price price, boolean displayed) {
        this.id = id;
        this.price = price;
        this.displayed = displayed;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }
}
