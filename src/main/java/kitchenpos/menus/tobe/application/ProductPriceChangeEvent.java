package kitchenpos.menus.tobe.application;

import java.util.UUID;

public record ProductPriceChangeEvent(
        UUID productId
) {
}
