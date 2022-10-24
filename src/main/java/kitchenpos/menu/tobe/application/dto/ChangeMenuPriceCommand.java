package kitchenpos.menu.tobe.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ChangeMenuPriceCommand {

    public final UUID id;

    public final BigDecimal price;

    public ChangeMenuPriceCommand(UUID id, BigDecimal price) {
        this.id = id;
        this.price = price;
    }
}
