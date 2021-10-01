package kitchenpos.products.tobe.domain;

import java.util.UUID;

public class ChangePriceEvent {
    private final UUID id;

    public ChangePriceEvent(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
