package kitchenpos.menu.tobe.application.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record MenuResponse(UUID id, String name, BigDecimal price, UUID menuGroupId,
                           List<MenuProductResponse> menuProducts) {
}
