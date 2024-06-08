package kitchenpos.menus.tobe.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MenuCreateRequest(
        String name,
        BigDecimal price,
        Boolean display,
        UUID menuGroupId,
        List<MenuProductRequest> menuProductRequests
) {

    public record MenuProductRequest(
            UUID productId,
            long quantity
    ) {
    }

}
