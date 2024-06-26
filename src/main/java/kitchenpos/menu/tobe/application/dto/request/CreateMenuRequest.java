package kitchenpos.menu.tobe.application.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateMenuRequest(BigDecimal price,
                                String name,
                                UUID menuGroupId,
                                boolean display,
                                List<CreateMenuProductRequest> menuProducts) {
}
