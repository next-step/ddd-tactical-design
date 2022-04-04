package kitchenpos.menus.tobe.domain.dto;

import kitchenpos.global.domain.vo.Price;

import java.util.UUID;

public final class ProductResponse {

    private final UUID id;
    private final Price price;

    public ProductResponse(UUID id, Price price) {
        this.id = id;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }
}
