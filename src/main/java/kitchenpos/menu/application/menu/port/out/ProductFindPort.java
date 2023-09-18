package kitchenpos.menu.application.menu.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductFindPort {

    Products find(final List<UUID> productIds);

    Optional<Product> find(final UUID productId);
}
