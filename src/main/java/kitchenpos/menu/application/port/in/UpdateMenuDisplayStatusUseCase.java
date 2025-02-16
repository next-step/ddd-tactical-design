package kitchenpos.menu.application.port.in;

import java.util.UUID;

public interface UpdateMenuDisplayStatusUseCase {
    void execute(UUID productId);
}
