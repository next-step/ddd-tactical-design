package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MenuCreateRequest(
        String name,
        BigDecimal price,
        UUID menuGroupId,
        boolean displayed,
        List<MenuProductCreateRequest> menuProducts
) {
}
