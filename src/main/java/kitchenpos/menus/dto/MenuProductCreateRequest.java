package kitchenpos.menus.dto;

import java.util.UUID;

public record MenuProductCreateRequest(
        UUID productId, long quantity
) {
}
