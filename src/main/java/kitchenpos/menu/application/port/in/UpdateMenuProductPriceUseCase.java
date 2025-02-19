package kitchenpos.menu.application.port.in;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateMenuProductPriceUseCase {
    void execute(UUID productId, BigDecimal price);
}
